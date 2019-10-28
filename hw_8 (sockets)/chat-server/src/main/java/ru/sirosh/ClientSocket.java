package ru.sirosh;

import ru.sirosh.Models.Message;
import ru.sirosh.Models.User;
import ru.sirosh.Repositories.MessageRepository;
import ru.sirosh.Repositories.MessageRepositoryJdbcImpl;
import ru.sirosh.Repositories.UsersRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientSocket extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private List<ClientSocket> clients;
    private User user;
    private Connection connection;
    public PrintWriter writer = null;
    private boolean auth = false;

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
            while (!auth && (line = reader.readLine()) != null) {
                if (line.equals("l;2;")) {
                    String username = reader.readLine();
                    String password = reader.readLine();
                    try {
                        User user = new UsersRepositoryJdbcImpl(connection).findOneByUsername(username).get();
                        if (user.password.equals(password)) {
                            this.user = user;
                            auth = true;
                            writer.println("l;1;");
                        } else {
                            auth = false;
                            writer.println("l;0;");
                        }
                    } catch (NoSuchElementException e) {
                        User user = new User(username, password);
                        new UsersRepositoryJdbcImpl(connection).save(user);
                        this.user = user;
                        writer.println("l;1;");
                        auth = true;
                    }

                }
            }
            while ((line = reader.readLine()) != null) {
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                new MessageRepositoryJdbcImpl(connection).save(new Message(user.id, line, currentTime));
                System.out.println("new msg from " + user.username + " : " + line);
                for (ClientSocket client : clients) {
                    if (client.auth) {
                        PrintWriter clientWriter = client.writer;
                        if (clientWriter != null) {
                            clientWriter.println(this.user.username + " : " + line);
                            clientWriter.flush();
                        }

                    }
                }
            }
            writer.close();
            writer = null;
            reader.close();
            socket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    //sending format:
    //first line is comand if format
    //c;n;
    //c - command
    //n - number of next lines
    //other n lines is data
}

