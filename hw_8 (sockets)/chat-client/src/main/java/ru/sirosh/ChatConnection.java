package ru.sirosh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatConnection {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void startMessageTasks() {
        Thread t2 = new Thread(receiveMessagesTask);
        t2.start();
        /*while (true){
            System.out.println(" t1 " + t1.getState());
            System.out.println(" t2 " + t2.getState());
        }*/

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

    private Runnable receiveMessagesTask = new Runnable() {
        public void run() {
            Scanner sc = new Scanner(System.in);
            try {
                while (true) {
                    String socketLine;
                    if ((socketLine = reader.readLine()) != null) {
                        System.out.println(socketLine);
                    } else if (sc.hasNextLine()) {
                        String systemLine = sc.nextLine();
                        System.out.print((char)13);
                        writer.println(systemLine);
                    }

                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    };
}









