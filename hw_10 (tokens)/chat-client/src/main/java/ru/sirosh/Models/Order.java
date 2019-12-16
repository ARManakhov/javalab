package ru.sirosh.Models;

import java.util.List;

public class Order {
    private Order(){}
    public long id;
    public long userId;
    public long addressId;
    public List<Product> productList;
    public Address address;
    public String status;

    public Order(long id, long userId, long addressId,String status) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
    }

    public Order(long id, long userId, long addressId, List<Product> productList, Address address) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.productList = productList;
        this.address = address;
    }
    public Order(long userId, long addressId) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
    }

    public Order(long userId, long addressId, List<Product> productList, Address address) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.productList = productList;
        this.address = address;
    }
    public Order(long userId, long addressId, List<Product> productList, Address address,String status) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.productList = productList;
        this.address = address;
        this.status  = status;
    }
}
