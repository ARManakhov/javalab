package dev.sirosh.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyRequest {
    public String header;

    public DummyRequest(String header) {
        this.header = header;
    }
    protected DummyRequest(){};
}
