package ru.sirosh.dto;

public class DtoProductList extends DtoProduct {

    private long offset;
    private long num;

    DtoProductList() {
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
}

