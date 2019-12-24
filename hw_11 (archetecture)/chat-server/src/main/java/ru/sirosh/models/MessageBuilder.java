package ru.sirosh.models;

import java.sql.Timestamp;

public final class MessageBuilder {
    private long id;
    private long senderId;
    private String text;
    private Timestamp timestamp;

    private MessageBuilder() {
    }

    public static MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    public MessageBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public MessageBuilder withSenderId(long senderId) {
        this.senderId = senderId;
        return this;
    }

    public MessageBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public MessageBuilder withTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Message build() {
        Message message = new Message();
        message.setId(id);
        message.setSenderId(senderId);
        message.setText(text);
        message.setTimestamp(timestamp);
        return message;
    }
}
