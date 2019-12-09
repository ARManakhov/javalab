package ru.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.*;
import ru.sirosh.Models.request.ReqWithMsgGetCom;
import ru.sirosh.Models.request.ReqWithMsgGetComPayload;
import ru.sirosh.Models.request.ReqWithString;
import ru.sirosh.Models.request.ReqWithUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleInHandler extends Thread {
    private StateHolder currentState;
    private PrintWriter writer;
    private BufferedReader reader;
    private ConsoleOutHandler t2;
    public ConsoleInHandler(PrintWriter writer, BufferedReader reader,ru.sirosh.StateHolder currentState) {
        this.writer = writer;
        this.reader = reader;
        this.currentState = currentState;
        t2 = new ConsoleOutHandler(writer,reader,currentState);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        long offset = 0;
        try {

            String socketLine;
            while (true) {

                if (sc.hasNextLine()) {
                    if (currentState.getState() == ru.sirosh.State.NOT_AUTH){
                        String systemLine = sc.nextLine();
                        if (systemLine.equals("1")){
                            System.out.println("Logging");
                            System.out.println("username:");
                            currentState.setState(ru.sirosh.State.NOT_AUTH_LOGGING);
                            continue;
                        } else if(systemLine.equals("2")){
                            System.out.println("Registration");
                            System.out.println("username:");
                            currentState.setState(ru.sirosh.State.NOT_AUTH_REGISTRATION);
                            continue;
                        } else {
                            System.out.println("for login type \"1\" for register type \"2\"");
                        }
                    } else if (currentState.getState() == ru.sirosh.State.NOT_AUTH_LOGGING || currentState.getState() == ru.sirosh.State.NOT_AUTH_REGISTRATION  ){
                        String username = sc.nextLine();
                        System.out.println("password:");
                        String password = sc.nextLine();
                        User user = new User(username,password,(long) -1);
                        ReqWithUser reqWithUser = currentState.getState() == ru.sirosh.State.NOT_AUTH_LOGGING ? new ReqWithUser("login",user) :  new ReqWithUser("register",user)   ;
                        writer.println(mapper.writeValueAsString(reqWithUser));
                        String response = reader.readLine();
                        if (((String) mapper.readValue(response, ReqWithString.class).payload).equals("success")){
                            System.out.println("success");
                            currentState.setState(ru.sirosh.State.AUTH);
                            System.out.println("you can see log if type /v");
                            if (t2.getState() == State.NEW)
                                t2.start();
                        } else {
                            System.out.println("Try again");
                            System.out.println("username:");
                        }
                        continue;
                    }else if (currentState.getState() == ru.sirosh.State.AUTH){
                        String text = sc.nextLine();
                        if (text.startsWith("/v")){
                            System.out.println("you now in log mode");
                            System.out.println("use q to quit and < or > for navigate");
                            currentState.setState(ru.sirosh.State.LOG);
                            offset = 0;
                            writer.println(mapper.writeValueAsString(new ReqWithMsgGetCom("msgs", new ReqWithMsgGetComPayload(offset,5))));
                        }else{
                            ReqWithString reqWithString = new ReqWithString("send",text);
                            writer.println(mapper.writeValueAsString(reqWithString));
                        }
                    }
                    else if (currentState.getState() == ru.sirosh.State.LOG){
                        String text = sc.nextLine();
                        if (text.startsWith("q")){
                            currentState.setState(ru.sirosh.State.AUTH);
                            System.out.println("returning to chat");
                        } else if (text.startsWith("<")){
                            offset+=5;
                            System.out.println("rewind <");
                            writer.println(mapper.writeValueAsString(new ReqWithMsgGetCom("msgs", new ReqWithMsgGetComPayload(offset,5))));

                        } else if (text.startsWith(">")){
                            if (offset - 5 >=0){
                                offset-=5;
                            }
                            writer.println(mapper.writeValueAsString(new ReqWithMsgGetCom("msgs", new ReqWithMsgGetComPayload(offset,5))));
                            System.out.println("rewind >");
                        }
                    } else {
                        continue;
                    }
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    public void trueSleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
