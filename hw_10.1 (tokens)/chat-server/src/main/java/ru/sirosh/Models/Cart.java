package ru.sirosh.Models;

import java.util.List;

public class Cart {
    private Cart(){}
    public long id;
    public long userId;
    public List<Product> productList;

    public Cart( long userId) {

        this.userId = userId;
    }

    public Cart(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Cart(long id, long userId, List<Product> productList) {
        this.id = id;
        this.userId = userId;
        this.productList = productList;
    }
}
