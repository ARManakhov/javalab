package dev.sirosh.models.request;

import dev.sirosh.models.Order;

public class ReqWithOrderToken extends DummyRequest {
    public Order order;
    public String token;
    public ReqWithOrderToken(String header, Order order) {
        super(header);
        this.order = order;
    }

    public ReqWithOrderToken(String header, Order order, String token) {
        super(header);
        this.order = order;
        this.token = token;
    }

    private ReqWithOrderToken() {
    }
}
