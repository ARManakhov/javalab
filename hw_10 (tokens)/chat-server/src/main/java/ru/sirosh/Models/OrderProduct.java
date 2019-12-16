package ru.sirosh.Models;

public class OrderProduct {
    public long orderId;
    public long productId;

    public OrderProduct(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
