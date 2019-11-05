
package ru.sirosh.Models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

public class RequestWithUser {

    private RequestWithUser(){

    }

    public RequestWithUser(String header, User payload) {
        this.header = header;
        this.payload = payload;
    }

    private String header;
    private User payload;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public User getPayload() {
        return payload;
    }

    public void setPayload(User payload) {
        this.payload = payload;
    }



}