package ru.sirosh.models.request;

public class ListGetComPayload {
    public long offset;
    public long number;

    private ListGetComPayload(){}

    public ListGetComPayload(long offset, long number) {
        this.offset = offset;
        this.number = number;
    }
}
