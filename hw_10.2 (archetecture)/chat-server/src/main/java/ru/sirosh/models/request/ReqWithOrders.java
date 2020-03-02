package ru.sirosh.models.request;

import ru.sirosh.models.Order;

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
