package ru.sirosh;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String[] ARG_LIB = new String[]{"--server-ip","--server-port"};

    public static void main(String[] args) {
        boolean clearArguments = true;
        Map<String,String> props = new HashMap<>();
        for (String arg: args) {
            String key = arg.split("=")[0];
            String value = arg.replace(key + "=", "");
            if (!props.containsKey(key)){
                props.put(key,value);
            } else {
                System.out.println("a few mentions of the argument " + key);
                clearArguments=false;
                break;
            }
        }

        for (String arg: ARG_LIB) {
            if (!props.containsKey(arg)){
                System.out.println("argument " + arg + " not found");
                clearArguments = false;
            }
        }
        if (clearArguments){
            System.out.println("ip : " +  props.get("--server-ip"));
            System.out.println("port : " +  props.get("--server-port"));
            ChatConnection chatClient = new ChatConnection();
            chatClient.startConnection(props.get("--server-ip"), Integer.parseInt(props.get("--server-port")));
            Scanner sc = new Scanner(System.in);
            boolean authorized = false;
            while (!authorized){
                System.out.println("username:");
                String username = sc.nextLine();
                System.out.println("password:");
                String password = sc.nextLine();
                chatClient.sendMessage("l;2;");
                chatClient.sendMessage(username);
                chatClient.sendMessage(password);
                if (chatClient.recieveLine().equals("l;1;")){
                    chatClient.startMessageTasks();
                    authorized = true;
                    System.out.println("succes");
                }else {
                    System.out.println("try again");
                }
            }

            while (true) {
                String message = sc.nextLine();
                chatClient.sendMessage(message);
            }

        }
    }
}

//java -jar chat-server.jar --port=6000
//        --db-properties-path=C:/myJava/db.properties
