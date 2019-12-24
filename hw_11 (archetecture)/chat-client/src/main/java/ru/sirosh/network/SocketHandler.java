package ru.sirosh.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.protocol.ResponseHandler;

import javax.swing.plaf.basic.BasicDesktopPaneUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectMapper mapper = new ObjectMapper();
    private ResponseHandler responseHandler;
    private boolean firstTime = true;

    public SocketHandler(Socket socket, ResponseHandler responseHandler) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(this.socket.getOutputStream(), true);
        this.responseHandler = responseHandler;
    }

    public void handle() throws IOException {
        firstTime = true;
        while (true) {
            Response response = null;
            if (!firstTime) {
                response = mapper.readValue(reqRespSeparate(), Response.class);
            } else {
                firstTime = false;
            }
            Request request = responseHandler.handle(response);
            writer.println(mapper.writeValueAsString(request));

        }
    }
    public String reqRespSeparate() throws IOException {
        while (true){
            String line = null;
            try {
                line = reader.readLine();
                mapper.readValue(line, Response.class);
                return line;
            } catch (JsonProcessingException e){

                responseHandler.handle(mapper.readValue(line,Request.class));
            }
        }
    }
}
