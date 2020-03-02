package ru.sirosh.dto;

public final class DtoProductListBuilder {
    private long offset;
    private long id;
    private long num;
    private String name;
    private String description;
    private double cost;
    private String token;

    private DtoProductListBuilder() {
    }

    public static DtoProductListBuilder aDtoProductList() {
        return new DtoProductListBuilder();
    }

    public DtoProductListBuilder withOffset(long offset) {
        this.offset = offset;
        return this;
    }

    public DtoProductListBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoProductListBuilder withNum(long num) {
        this.num = num;
        return this;
    }

    public DtoProductListBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DtoProductListBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public DtoProductListBuilder withCost(double cost) {
        this.cost = cost;
        return this;
    }

    public DtoProductListBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoProductList build() {
        DtoProductList dtoProductList = new DtoProductList();
        dtoProductList.setOffset(offset);
        dtoProductList.setId(id);
        dtoProductList.setNum(num);
        dtoProductList.setName(name);
        dtoProductList.setDescription(description);
        dtoProductList.setCost(cost);
        dtoProductList.setToken(token);
        return dtoProductList;
    }
}
