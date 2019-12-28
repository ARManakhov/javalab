package dev.sirosh.dto;

import java.util.List;

public final class DtoOrderBuilder {
    public long id;
    public List<DtoProduct> dtoProductList;
    public DtoAddress dtoAddress;
    public String status;
    public String token;

    private DtoOrderBuilder() {
    }

    public static DtoOrderBuilder aDtoOrder() {
        return new DtoOrderBuilder();
    }

    public DtoOrderBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoOrderBuilder withDtoProductList(List<DtoProduct> dtoProductList) {
        this.dtoProductList = dtoProductList;
        return this;
    }

    public DtoOrderBuilder withDtoAddress(DtoAddress dtoAddress) {
        this.dtoAddress = dtoAddress;
        return this;
    }

    public DtoOrderBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public DtoOrderBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoOrder build() {
        DtoOrder dtoOrder = new DtoOrder();
        dtoOrder.setId(id);
        dtoOrder.setDtoProductList(dtoProductList);
        dtoOrder.setDtoAddress(dtoAddress);
        dtoOrder.setStatus(status);
        dtoOrder.setToken(token);
        return dtoOrder;
    }
}
