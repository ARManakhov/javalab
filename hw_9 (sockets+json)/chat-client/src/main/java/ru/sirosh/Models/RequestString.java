package ru.sirosh.Models;

public class RequestString {
    public String header;
    public String payload;

    private RequestString(){

    }

    public RequestString(String header, String payload) {
        this.header = header;
        this.payload = payload;
    }
}
