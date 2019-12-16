package ru.sirosh.Models;

public class CartProduct {
    public long cartId;
    public long productId;

    public CartProduct(long cartId, long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }
}
