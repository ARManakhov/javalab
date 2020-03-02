package ru.sirosh.network;

import ru.sirosh.protocol.ResponseHandler;

import java.io.IOException;
import java.net.Socket;

public class SocketGetter {

    private ResponseHandler responseHandler;
    private String ip;
    private String port;
    private Socket socket;

    public SocketGetter(ResponseHandler responseHandler, String ip, String port) {
        this.responseHandler = responseHandler;
        this.ip = ip;
        this.port = port;
    }

    public SocketHandler getSocketHandler() throws IOException {
        socket = new Socket(ip, Integer.parseInt(port));
        return new SocketHandler(socket,responseHandler);
    }
}
