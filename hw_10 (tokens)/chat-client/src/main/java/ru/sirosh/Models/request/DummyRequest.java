package ru.sirosh.Models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyRequest {
    public String header;

    public DummyRequest(String header) {
        this.header = header;
    }
    protected DummyRequest(){};
}
