package ru.sirosh.dto;

public class DtoGetList implements Dto {
    private long offset;
    private long num;
    private String token;

    private DtoGetList() {
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DtoGetList(long offset, long num, String token) {
        this.offset = offset;
        this.num = num;
        this.token = token;
    }
}
