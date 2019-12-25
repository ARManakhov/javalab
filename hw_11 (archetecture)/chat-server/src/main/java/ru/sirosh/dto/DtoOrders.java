package ru.sirosh.dto;

import java.util.List;

public class DtoOrders implements Dto {
    List<DtoOrder> orders;

    public List<DtoOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DtoOrder> orders) {
        this.orders = orders;
    }

    private DtoOrders() {
    }

    public DtoOrders(List<DtoOrder> orders) {
        this.orders = orders;
    }
}
