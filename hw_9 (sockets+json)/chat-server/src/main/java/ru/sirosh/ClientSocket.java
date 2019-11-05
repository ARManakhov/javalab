package ru.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sirosh.Models.*;
import ru.sirosh.Repositories.MessageRepositoryJdbcImpl;
import ru.sirosh.Repositories.UsersRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientSocket extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private List<ClientSocket> clients;
    private User user = null;
    private Connection connection;
    private PrintWriter writer = null;
    private ObjectMapper mapper = new ObjectMapper();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private boolean logMode = false;

    public ClientSocket(Socket socket, List<ClientSocket> clients, Connection connection) {
        this.connection = connection;
        this.socket = socket;
        this.clients = clients;
        clients.add(this);
    }

    @Override
    public void run() {
        System.out.println("in run");
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 1);
            String line;
            writer = new PrintWriter(this.socket.getOutputStream(), true);
            while ((line = reader.readLine()) != null) {
                requestHandler(line);
            }
            writer.close();
            writer = null;
            reader.close();
            socket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestHandler(String line) throws IOException {

        System.out.println("new line : " + line);
        Request request = mapper.readValue(line, Request.class);
        if (user == null && request.header.equals("login") ){
            RequestWithUser requestWithUser = mapper.readValue(line, RequestWithUser.class);
            User userFromJson = requestWithUser.getPayload();
            try {
                User userFromDb = new UsersRepositoryJdbcImpl(connection).findOneByUsername(userFromJson.getUsername()).get();
                if (encoder.matches(userFromJson.getPassword(),userFromDb.getPassword())) {
                    user = userFromDb;
                    writer.println(mapper.writeValueAsString(new Request("login","success")));
                } else {
                    writer.println(mapper.writeValueAsString(new Request("login","fail")));
                }
            } catch (NoSuchElementException e) {
                writer.println(mapper.writeValueAsString(new Request("login","fail")));
            }
        }
        if (user == null && request.header.equals("register") ){
            RequestWithUser requestWithUser = mapper.readValue(line, RequestWithUser.class);
            User userFromJson = requestWithUser.getPayload();
            userFromJson.setPassword(encoder.encode(userFromJson.getPassword()));
            try {
                new UsersRepositoryJdbcImpl(connection).findOneByUsername(userFromJson.getUsername()).get();
                writer.println(mapper.writeValueAsString(new Request("login","fail")));
            } catch (NoSuchElementException e) {
                new UsersRepositoryJdbcImpl(connection).save(userFromJson);
                user = userFromJson;
                writer.println(mapper.writeValueAsString(new Request("login","success")));
            }
        }
        if (user != null && request.header.equals("send") ) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            System.out.println("sending:" + request.payload);
            new MessageRepositoryJdbcImpl(connection).save(new Message(user.getId(), (String) request.payload, currentTime));
            for (ClientSocket client : clients) {
                if (client.user!=null) {
                    PrintWriter clientWriter = client.writer;
                    if (clientWriter != null) {
                        clientWriter.println(mapper.writeValueAsString(new Request("newMsg",this.user.getUsername() + " : " +  request.payload)));
                    }
                }
            }
            writer.println(mapper.writeValueAsString(new Request("send","success")));
        }
        if (user != null && request.header.equals("msgs") ) {
            Request requestLogMode = mapper.readValue(line, Request.class);
            System.out.println(requestLogMode.payload);
            LinkedHashMap<String, Integer> data = (LinkedHashMap<String, Integer>) requestLogMode.payload;
            List<Message> msgs = new MessageRepositoryJdbcImpl(connection).findLastMessageWithOffset(data.get("payload"), data.get("offset"));
            writer.println(mapper.writeValueAsString(new ReqWitmMsgs("msgs",msgs)));
            System.out.println(msgs.get(1).id);
        }
    }

}

