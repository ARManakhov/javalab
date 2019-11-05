package ru.sirosh.Models;

public class Request {
    public String header;
    public Object payload;

    public Request(String header, Object payload) {
        this.header = header;
        this.payload = payload;
    }

    public Request() {
    }
}
