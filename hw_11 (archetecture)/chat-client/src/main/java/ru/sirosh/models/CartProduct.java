package ru.sirosh.models;

public class CartProduct {
    private long cartId;
    private long productId;

    public CartProduct(long cartId, long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public long getCartId() {
        return cartId;
    }

    public long getProductId() {
        return productId;
    }
}
