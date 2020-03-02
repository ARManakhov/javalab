package dev.sirosh.models;

public class OrderProduct {
    private long orderId;
    private long productId;

    public OrderProduct(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }
}
