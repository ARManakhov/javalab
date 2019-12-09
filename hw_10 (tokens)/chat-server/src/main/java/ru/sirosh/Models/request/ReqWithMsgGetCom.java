package ru.sirosh.Models.request;

public class ReqWithMsgGetCom extends DummyRequest {


    public ReqWithMsgGetComPayload payload;

    private ReqWithMsgGetCom(){}

    public ReqWithMsgGetCom(String header, ReqWithMsgGetComPayload payload) {
        super(header);
        this.payload = payload;
    }
}
