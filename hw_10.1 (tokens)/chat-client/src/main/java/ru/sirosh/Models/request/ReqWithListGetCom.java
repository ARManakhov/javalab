package ru.sirosh.Models.request;

public class ReqWithListGetCom extends DummyRequest {


    public ListGetComPayload payload;

    private ReqWithListGetCom(){}

    public ReqWithListGetCom(String header, ListGetComPayload payload) {
        super(header);
        this.payload = payload;
    }
}
