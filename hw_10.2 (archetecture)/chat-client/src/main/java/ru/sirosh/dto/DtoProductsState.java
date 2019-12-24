package ru.sirosh.dto;

import java.util.List;

public class DtoProductsState extends DtoProducts {
    String status;

    private DtoProductsState() {
    }

    public DtoProductsState(List<DtoProduct> products, long totalCount, String status) {
        super(products, totalCount);
        this.status = status;
    }
}
