package ru.sirosh.Models;

public class PayloadLogMode {
    public long payload;
    public long offset;

    public PayloadLogMode(long num, long offset) {
        this.payload = num;
        this.offset = offset;
    }
}
