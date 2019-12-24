package ru.sirosh.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sun.jmx.remote.internal.ArrayQueue;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.protocol.ResponseHandler;

import javax.swing.plaf.basic.BasicDesktopPaneUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SocketHandler {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectMapper mapper = new ObjectMapper();
    private ResponseHandler responseHandler;
    private boolean firstTime = true;
    private BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(10);

    public SocketHandler(Socket socket, ResponseHandler responseHandler) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(this.socket.getOutputStream(), true);
        this.responseHandler = responseHandler;

    }

    public void handle() throws IOException {
        firstTime = true;

        DataGetter dataGetter = new DataGetter(blockingQueue, reader, responseHandler);
        dataGetter.start();
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
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
