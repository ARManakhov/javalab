package ru.sirosh.dto;

import java.util.List;

public final class DtoProductsStateBuilder {
    String status;
    long totalCount;
    private List<DtoProduct> products;

    private DtoProductsStateBuilder() {
    }

    public static DtoProductsStateBuilder aDtoProductsState() {
        return new DtoProductsStateBuilder();
    }

    public DtoProductsStateBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public DtoProductsStateBuilder withProducts(List<DtoProduct> products) {
        this.products = products;
        return this;
    }

    public DtoProductsStateBuilder withTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public DtoProductsState build() {
        return new DtoProductsState(products, totalCount, status);
    }
}
