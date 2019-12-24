package ru.sirosh.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.sirosh.dto.Dto;

import java.util.Map;
public class Request<T extends Dto> {
    private String command;
    private T data;

    private Request() {
    }

    public Request(String command, T data) {
        this.command = command;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", data=" + data +
                '}';
    }
}