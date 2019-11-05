package ru.sirosh.Models;

import java.util.ArrayList;
import java.util.List;

public class ReqWitmMsgs {
    public String header;
    public List<Message> payload;

    public ReqWitmMsgs() {
    }

    public ReqWitmMsgs(String header, List<Message> payload) {
        this.header = header;
        this.payload = payload;
    }
}
