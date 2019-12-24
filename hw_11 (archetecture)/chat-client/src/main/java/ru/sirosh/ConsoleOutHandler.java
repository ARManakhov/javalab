package ru.sirosh;
/*
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.request.DummyRequest;
import ru.sirosh.Models.request.ReqWithString;
import ru.sirosh.view.StateHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConsoleOutHandler extends Thread {
    private PrintWriter writer;
    private BufferedReader reader;
    private StateHolder currentState;

    public ConsoleOutHandler(PrintWriter writer, BufferedReader reader, StateHolder currentState) {
        this.writer = writer;
        this.reader = reader;
        this.currentState = currentState;

    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        while (true){
            if ((currentState.getState() == ru.sirosh.State.CHAT)){
                String socketLine;
                try {
                    if ((socketLine = reader.readLine()) != null){
                        DummyRequest req = mapper.readValue(socketLine, DummyRequest.class);
                        if(req.header.equals("newMsg")){
                            System.out.println(mapper.readValue(socketLine, ReqWithString.class).payload);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
*/