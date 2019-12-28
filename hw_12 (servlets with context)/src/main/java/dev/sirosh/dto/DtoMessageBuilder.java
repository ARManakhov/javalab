package dev.sirosh.dto;

import java.sql.Timestamp;

public final class DtoMessageBuilder {
    private long id;
    private long senderId;
    private String text;
    private Timestamp timestamp;
    private String token;
    private String username;

    private DtoMessageBuilder() {
    }

    public static DtoMessageBuilder aDtoMessage() {
        return new DtoMessageBuilder();
    }

    public DtoMessageBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoMessageBuilder withSenderId(long senderId) {
        this.senderId = senderId;
        return this;
    }

    public DtoMessageBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public DtoMessageBuilder withTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public DtoMessageBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoMessageBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DtoMessage build() {
        DtoMessage dtoMessage = new DtoMessage();
        dtoMessage.setId(id);
        dtoMessage.setSenderId(senderId);
        dtoMessage.setText(text);
        dtoMessage.setTimestamp(timestamp);
        dtoMessage.setToken(token);
        dtoMessage.setUsername(username);
        return dtoMessage;
    }
}
