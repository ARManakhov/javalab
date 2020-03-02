package ru.sirosh.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.protocol.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class DataGetter extends Thread {
    private BufferedReader reader;
    private ObjectMapper mapper = new ObjectMapper();
    private ResponseHandler responseHandler;
    private BlockingQueue<String> queue;
    public DataGetter(BlockingQueue<String> queue, BufferedReader reader, ResponseHandler responseHandler) {
        this.reader = reader;
        this.responseHandler = responseHandler;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            String line = null;
            try {
                try {
                    line = reader.readLine();
                    mapper.readValue(line, Response.class);
                    try {
                        queue.put(line);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (JsonProcessingException e) {
                    responseHandler.handle(mapper.readValue(line, Request.class));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
