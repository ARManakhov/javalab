package ru.sirosh.models.request;

import ru.sirosh.models.Product;

public class ReqWithProduct extends DummyRequest{
    public Product product;

    public ReqWithProduct() {
    }

    public ReqWithProduct(String header, Product product) {
        super(header);
        this.product = product;
    }
}
