package ru.sirosh.Models.request;

import ru.sirosh.Models.Order;

import java.util.List;

public class ReqWithOrders extends DummyRequest{
    public List<Order> orders;

    private ReqWithOrders() {
    }

    public ReqWithOrders(String header, List<Order> orders) {
        super(header);
        this.orders = orders;
    }
}
