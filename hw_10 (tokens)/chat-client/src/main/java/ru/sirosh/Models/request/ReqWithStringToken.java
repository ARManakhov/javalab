package ru.sirosh.Models.request;

public class ReqWithStringToken extends DummyRequest{
    public String payload;
    public String token;
    private ReqWithStringToken(){}

    public ReqWithStringToken(String header, String payload) {
        super(header);
        this.payload = payload;
    }

    public ReqWithStringToken(String header, String payload, String token) {
        super(header);
        this.payload = payload;
        this.token = token;
    }
}
