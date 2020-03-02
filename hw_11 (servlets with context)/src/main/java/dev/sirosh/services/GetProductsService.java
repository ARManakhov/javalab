package dev.sirosh.services;

import dev.sirosh.dto.*;
import dev.sirosh.models.Product;
import dev.sirosh.repositories.ProductRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetProductsService {
    Connection dbConnection;


    public GetProductsService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoGetList dtoGetList) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        List<DtoProduct> products = new ArrayList<>();
        List<Product> dbProducts = prji.getList(dtoGetList.getNum(), dtoGetList.getOffset());
        for (Product p : dbProducts) {
            products.add(DtoProductBuilder.aDtoProduct()
                    .withName(p.getName())
                    .withCost(p.getCost())
                    .withId(p.getId())
                    .withDescription(p.getDescription())
                    .build());
        }
        return new DtoProducts(products, prji.getCount());
    }
}
