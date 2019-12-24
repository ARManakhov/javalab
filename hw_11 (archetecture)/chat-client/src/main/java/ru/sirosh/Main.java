package ru.sirosh;

import ru.sirosh.configs.ArgumentProperty;
import ru.sirosh.configs.ArgumentReader;
import ru.sirosh.network.SocketGetter;
import ru.sirosh.network.SocketHandler;
import ru.sirosh.protocol.ResponseHandler;
import ru.sirosh.view.StateHolder;
import ru.sirosh.view.View;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String[] ARG_LIB = new String[]{"--server-ip", "--server-port"};

    public static void main(String[] args) {
        try {
            ArgumentProperty property = new ArgumentReader(args).getProperties();
            try {
                StateHolder currentState = new StateHolder(State.NOT_AUTH);
                View view = new View(currentState);
                ResponseHandler responseHandler = new ResponseHandler(view);
                SocketHandler socketHandler = new SocketGetter(responseHandler,property.getIp(),property.getPort()).getSocketHandler();
                socketHandler.handle();
            } catch (IOException e) {
                System.out.println("socket can't connect ");
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("wrong arguments");
        }

    }
}

