package ru.sirosh.protocol;


import ru.sirosh.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// обрабатывает запрос на уровне протокола
public class ResponseHandler {

    View view;

    public ResponseHandler(View view) {
        this.view = view;
    }

    public Request handle(Response response){
       while (true){
           Request req = view.render(response);
        if (req != null){
            return req;
        }
       }
    }

    public void handle(Request request){
        view.render(request);
    }
}