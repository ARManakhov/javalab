package ru.sirosh.models.request;

import ru.sirosh.models.Message;
import ru.sirosh.models.User;

import java.util.List;

public class ReqWitmMsgs extends DummyRequest{
    public List<Message> messages;
    public List<User> users;
    public long count;
    private ReqWitmMsgs(){}

    public ReqWitmMsgs(String header, List<Message> messages, List<User> users,long count) {
        super(header);
        this.messages = messages;
        this.users = users;
        this.count=count;
    }

}
