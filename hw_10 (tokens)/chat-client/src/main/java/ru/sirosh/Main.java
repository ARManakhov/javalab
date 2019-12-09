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
            System.out.println("Welcone to chat client");

            ChatConnection chatClient = new ChatConnection();
            chatClient.startConnection(props.get("--server-ip"), Integer.parseInt(props.get("--server-port")));

            }



        }
    }


//java -jar chat-server.jar --port=6000
//        --db-properties-path=C:/myJava/db.properties
