package ru.sirosh.Models;

public class PayloadLogMode {
    public String header;
    public long payload;
    public long offset;

    public PayloadLogMode(String header, long num, long offset) {
        this.header = header;
        this.payload = num;
        this.offset = offset;
    }
}
