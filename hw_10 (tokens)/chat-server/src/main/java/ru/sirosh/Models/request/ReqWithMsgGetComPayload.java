package ru.sirosh.Models.request;

public class ReqWithMsgGetComPayload {
    public long offset;
    public long number;

    private ReqWithMsgGetComPayload(){}

    public ReqWithMsgGetComPayload(long offset, long number) {
        this.offset = offset;
        this.number = number;
    }
}
