package ru.sirosh.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Request {
    public String header;
    public Object payload;
    private Request(){

    }
    public Request(String header, Object payload) {
        this.header = header;
        this.payload = payload;
    }
}
