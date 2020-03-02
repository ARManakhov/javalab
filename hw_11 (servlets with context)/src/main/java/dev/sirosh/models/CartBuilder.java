package dev.sirosh.models;

import java.util.List;

public final class CartBuilder {
    private long id;
    private long userId;
    private List<Product> productList;

    private CartBuilder() {
    }

    public static CartBuilder aCart() {
        return new CartBuilder();
    }

    public CartBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CartBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public CartBuilder withProductList(List<Product> productList) {
        this.productList = productList;
        return this;
    }

    public Cart build() {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUserId(userId);
        cart.setProductList(productList);
        return cart;
    }
}
