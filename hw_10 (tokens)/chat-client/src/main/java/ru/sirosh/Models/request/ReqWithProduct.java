package ru.sirosh.Models.request;

import ru.sirosh.Models.Product;

public class ReqWithProduct extends DummyRequest{
    public Product product;

    public ReqWithProduct() {
    }

    public ReqWithProduct(String header, Product product) {
        super(header);
        this.product = product;
    }
}
