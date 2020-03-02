package ru.sirosh.models.request;

import ru.sirosh.models.Product;

import java.util.List;

public class ReqWithProducts extends DummyRequest{
    public List<Product> products;
    public Long count;

    private ReqWithProducts() {
    }

    public ReqWithProducts(String header, List<Product> products, Long count) {
        super(header);
        this.products = products;
        this.count = count;
    }
}
