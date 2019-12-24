package ru.sirosh.models.request;

import ru.sirosh.models.Order;

public class ReqWithOrder extends DummyRequest {
    public Order order;

    public ReqWithOrder(String header, Order order) {
        super(header);
        this.order = order;
    }

    private ReqWithOrder() {
    }
}
