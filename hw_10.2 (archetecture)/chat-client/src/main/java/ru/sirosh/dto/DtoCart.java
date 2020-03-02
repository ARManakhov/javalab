package ru.sirosh.dto;

import java.util.List;

public class DtoCart implements Dto {
    DtoCart(){}
    private long id;
    private long userId;
    private List<DtoProduct> dtoProductList;
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<DtoProduct> getDtoProductList() {
        return dtoProductList;
    }

    public void setDtoProductList(List<DtoProduct> dtoProductList) {
        this.dtoProductList = dtoProductList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
