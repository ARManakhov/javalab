package ru.sirosh.dto;

import java.util.List;

public class DtoProducts implements Dto {
    private List<DtoProduct> products;
    long totalCount;

    public List<DtoProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DtoProduct> products) {
        this.products = products;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    protected DtoProducts() {
    }

    public DtoProducts(List<DtoProduct> products, long totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }
}
