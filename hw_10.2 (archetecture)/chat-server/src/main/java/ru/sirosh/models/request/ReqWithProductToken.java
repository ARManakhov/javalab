package ru.sirosh.models.request;

import ru.sirosh.models.Product;

public class ReqWithProductToken extends DummyRequest{
    public Product product;
    public String token;
    public ReqWithProductToken() {
    }

    public ReqWithProductToken(String header, Product product, String token) {
        super(header);
        this.product = product;
        this.token = token;
    }

    public ReqWithProductToken(String header, Product product) {
        super(header);
        this.product = product;
    }
}
