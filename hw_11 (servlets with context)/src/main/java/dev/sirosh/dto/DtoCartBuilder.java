package dev.sirosh.dto;

import java.util.List;

public final class DtoCartBuilder {
    public long id;
    public long userId;
    public List<DtoProduct> dtoProductList;
    public String token;

    private DtoCartBuilder() {
    }

    public static DtoCartBuilder aDtoCart() {
        return new DtoCartBuilder();
    }

    public DtoCartBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoCartBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public DtoCartBuilder withDtoProductList(List<DtoProduct> dtoProductList) {
        this.dtoProductList = dtoProductList;
        return this;
    }

    public DtoCartBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoCart build() {
        DtoCart dtoCart = new DtoCart();
        dtoCart.setId(id);
        dtoCart.setUserId(userId);
        dtoCart.setDtoProductList(dtoProductList);
        dtoCart.setToken(token);
        return dtoCart;
    }
}
