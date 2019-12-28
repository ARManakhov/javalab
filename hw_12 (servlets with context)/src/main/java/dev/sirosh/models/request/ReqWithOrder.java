package dev.sirosh.models.request;

import dev.sirosh.models.Order;

public class ReqWithOrder extends DummyRequest {
    public Order order;

    public ReqWithOrder(String header, Order order) {
        super(header);
        this.order = order;
    }

    private ReqWithOrder() {
    }
}
