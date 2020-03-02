package ru.sirosh.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.sirosh.dto.Dto;

import java.util.Map;
public class Request {
    private String command;
    private Dto data;

    public String getCommand() {
        return command;
    }

    public Dto getData() {
        return data;
    }

    private Request() {
    }

    public Request(String command, Dto data) {
        this.command = command;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", data=" + data +
                '}';
    }
}