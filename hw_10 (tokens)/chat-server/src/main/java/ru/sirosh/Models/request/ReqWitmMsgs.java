package ru.sirosh.Models.request;

import ru.sirosh.Models.Message;

import java.util.List;

public class ReqWitmMsgs extends DummyRequest{
    public List<Message> payload;

    private ReqWitmMsgs(){}

    public ReqWitmMsgs(String header, List<Message> payload) {
        super(header);
        this.payload = payload;
    }
}
