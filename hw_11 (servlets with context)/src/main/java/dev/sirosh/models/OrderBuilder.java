package dev.sirosh.models;

import java.util.List;

public final class OrderBuilder {
    private long id;
    private long userId;
    private long addressId;
    private List<Product> productList;
    private Address address;
    private String status;

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder withAddressId(long addressId) {
        this.addressId = addressId;
        return this;
    }

    public OrderBuilder withProductList(List<Product> productList) {
        this.productList = productList;
        return this;
    }

    public OrderBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public OrderBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setProductList(productList);
        order.setAddress(address);
        order.setStatus(status);
        return order;
    }
}
