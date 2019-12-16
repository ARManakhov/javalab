package ru.sirosh.Models.request;

import ru.sirosh.Models.Message;
import ru.sirosh.Models.User;

import java.util.List;
import java.util.Set;

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
