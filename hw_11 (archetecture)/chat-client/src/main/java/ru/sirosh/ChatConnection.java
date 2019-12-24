package ru.sirosh;
/*
import ru.sirosh.view.StateHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatConnection {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        System.out.println("connecting");
        while (clientSocket == null){
            try {
                clientSocket = new Socket(ip, port);
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.print(".");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println();
        startMessageTasks();
    }

    public void startMessageTasks() {
        StateHolder currentState = new StateHolder(ru.sirosh.State.NOT_AUTH);

        ConsoleInHandler t1 = new ConsoleInHandler(writer,reader,currentState);
        t1.start();



    }

    public String recieveLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }


}*/









