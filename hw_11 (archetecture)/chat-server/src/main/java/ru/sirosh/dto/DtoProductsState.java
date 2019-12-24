package ru.sirosh.dto;

import java.util.List;

public class DtoProductsState extends DtoProducts {
    boolean success;

    public DtoProductsState(List<DtoProduct> products, int totalCount, boolean success) {
        super(products, totalCount);
        this.success = success;
    }
}
