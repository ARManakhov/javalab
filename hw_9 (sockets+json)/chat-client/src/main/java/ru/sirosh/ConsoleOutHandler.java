package ru.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.Message;
import ru.sirosh.Models.ReqWitmMsgs;
import ru.sirosh.Models.Request;
import ru.sirosh.Models.RequestString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
            if ((currentState.getState() == ru.sirosh.State.AUTH || currentState.getState() == ru.sirosh.State.LOG)){
                String socketLine;
                try {
                    if ((socketLine = reader.readLine()) != null){
                        Request request = mapper.readValue(socketLine, Request.class);
                        if( request.header.equals("newMsg")){
                            System.out.println(mapper.readValue(socketLine, RequestString.class).payload);
                        }else if(request.header.equals("msgs")){
                            List<Message> msgs = mapper.readValue(socketLine, ReqWitmMsgs.class).payload;
                            for (Message msg: msgs ) {
                                System.out.println("user with id " + msg.senderId + ":" + msg.text);
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public void trueSleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
