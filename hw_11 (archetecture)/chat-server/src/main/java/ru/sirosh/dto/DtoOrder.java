package ru.sirosh.dto;

import java.util.List;

public class DtoOrder implements Dto  {
    DtoOrder(){}
    private long id;
    private List<DtoProduct> dtoProductList;
    private DtoAddress dtoAddress;
    private String status;
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<DtoProduct> getDtoProductList() {
        return dtoProductList;
    }

    public void setDtoProductList(List<DtoProduct> dtoProductList) {
        this.dtoProductList = dtoProductList;
    }

    public DtoAddress getDtoAddress() {
        return dtoAddress;
    }

    public void setDtoAddress(DtoAddress dtoAddress) {
        this.dtoAddress = dtoAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
