package ru.sirosh.dto;

import java.util.List;

public class DtoMessages implements Dto {
    private List<DtoMessage> messages;
    long totalCount;

    public List<DtoMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<DtoMessage> messages) {
        this.messages = messages;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    protected DtoMessages() {
    }

    public DtoMessages(List<DtoMessage> messages, long totalCount) {
        this.messages = messages;
        this.totalCount = totalCount;
    }
}
