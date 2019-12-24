package ru.sirosh.configs;

import java.util.HashMap;
import java.util.Map;

public class ArgumentReader {
    String[] args;
    private static final String[] ARG_LIB = new String[]{"--server-ip", "--server-port"};

    public ArgumentReader(String[] args) {
        this.args = args;
    }

    public ArgumentProperty getProperties() {
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

        for (String arg : ARG_LIB) {
            if (!props.containsKey(arg)) {
                System.out.println("argument " + arg + " not found");
                clearArguments = false;
            }
        }
        if (clearArguments) {
            System.out.println("ip : " + props.get("--server-ip"));
            System.out.println("port : " + props.get("--server-port"));
        } else throw new IllegalArgumentException();
        return new ArgumentProperty(props.get("--server-ip"), props.get("--server-port"));
    }
}
