package dev.sirosh.models.request;

public class ReqWithString extends DummyRequest{
    public String payload;

    private ReqWithString(){}

    public ReqWithString(String header, String payload) {
        super(header);
        this.payload = payload;
    }
}
