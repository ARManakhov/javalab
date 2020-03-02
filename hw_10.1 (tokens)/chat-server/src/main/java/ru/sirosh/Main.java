package ru.sirosh;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String[] ARG_LIB = new String[]{"--port", "--db-properties-path"};

    public static void main(String[] args) {
        boolean clearArguments = true;
        Map<String, String> props = new HashMap<>();
        for (String arg : args) {
            String key = arg.split("=")[0];
            String value = arg.replace(key + "=", "");
            if (!props.containsKey(key)) {
                props.put(key, value);
            } else {
                System.out.println("a few mentions of the argument " + key);
                clearArguments = false;
                break;
            }
        }
        if (clearArguments)
            for (String arg : ARG_LIB) {
                if (!props.containsKey(arg)) {
                    System.out.println("argument " + arg + " not found");
                    clearArguments = false;
                    break;
                }
            }

        if (clearArguments) {                                                   //start here
            System.out.println("port " + props.get("--port"));
            System.out.println("path " + props.get("--db-properties-path"));
            SocketsProcessor sp = new SocketsProcessor();
            sp.start(Integer.parseInt(props.get("--port")));

        }
    }
}

