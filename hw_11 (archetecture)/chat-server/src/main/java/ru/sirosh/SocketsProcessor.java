package ru.sirosh;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocketsProcessor {
    private ServerSocket serverSocket;
    private List<ClientSocket> clients;

    public SocketsProcessor() {
        clients = new ArrayList<ClientSocket>();
    }

    public void start(int port) {
        Connection connection = null;
        try {
            DBPropReader pr = new DBPropReader("db.properties");
            connection = new DBConnection(pr.read()).connect();
            serverSocket = new ServerSocket(port);
        } catch (IOException | SQLException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                if (connection != null) {
                    ClientSocket clientSocket = new ClientSocket(serverSocket.accept(), clients, connection);
                    clientSocket.start();
                } else {
                    System.out.println("db error");
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
*/