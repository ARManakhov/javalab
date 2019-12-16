package ru.sirosh.Models.request;

import ru.sirosh.Models.Product;

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
