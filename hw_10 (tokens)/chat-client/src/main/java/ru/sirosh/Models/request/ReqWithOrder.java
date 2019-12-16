package ru.sirosh.Models.request;

import ru.sirosh.Models.Order;

public class ReqWithOrder extends DummyRequest {
    public Order order;

    public ReqWithOrder(String header, Order order) {
        super(header);
        this.order = order;
    }

    private ReqWithOrder() {
    }
}
