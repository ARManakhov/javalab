package ru.sirosh.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.dto.DtoMessage;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.RequestsHandler;
import ru.sirosh.protocol.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketsManager extends Thread {
    private RequestsHandler requestsHandler;
    private int port;
    private List<SocketHandler> handlers;
    private ServerSocket serverSocket;
    private ObjectMapper mapper = new ObjectMapper();


    public SocketsManager(RequestsHandler requestsHandler, int port) throws IOException {
        this.requestsHandler = requestsHandler;
        this.port = port;
        handlers = new ArrayList<>();
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketHandler socketHandler = new SocketHandler(serverSocket.accept(), requestsHandler);
                socketHandler.start();
                handlers.add(socketHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEveryone(Request<DtoMessage> request) {
        for (SocketHandler socketHandler : handlers) {
            try {
                socketHandler.getWriter().println(mapper.writeValueAsString(request));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
