package ru.sirosh.Models;

import java.sql.Timestamp;

public class Message {
    public long id;

    public long senderId;
    public String text;
    public Timestamp timestamp;

    public Message(long id, long senderId, String text, Timestamp timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message(long senderId, String text, Timestamp timestamp) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.text = text;
    }
}
