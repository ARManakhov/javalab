package ru.sirosh.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.RequestsHandler;
import ru.sirosh.protocol.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class SocketHandler extends Thread {
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private RequestsHandler requestsHandler;
    private ObjectMapper mapper = new ObjectMapper();

    public PrintWriter getWriter() {
        return writer;
    }


    public SocketHandler(Socket socket, RequestsHandler requestsHandler) throws IOException {
        this.socket = socket;
        this.requestsHandler = requestsHandler;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(this.socket.getOutputStream(), true);

    }

    @Override
    public void run() {
        System.out.println("new connection with socket");
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("new req " + line);
                Request request = mapper.readValue(line, Request.class);
                Response response = requestsHandler.handleRequest(request);
                writer.println(mapper.writeValueAsString(response));
                System.out.println("response " + mapper.writeValueAsString(response));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
