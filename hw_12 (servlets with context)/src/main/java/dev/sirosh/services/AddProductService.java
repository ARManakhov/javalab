package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.*;
import dev.sirosh.models.Product;
import dev.sirosh.models.ProductBuilder;
import dev.sirosh.models.User;
import dev.sirosh.repositories.ProductRepositoryJdbcImpl;
import dev.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AddProductService {

    Connection dbConnection;


    public AddProductService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    public Dto execute(DtoProductList dtoProduct) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
        User reqUser = new TokenizeUser().decodeJwt(dtoProduct.getToken());
        User user = urji.findOneById(reqUser.getId()).get();
        String status = null;
        if (user.getRole().equals("admin")) {
            Product product = ProductBuilder.aProduct()
                    .withDescription(dtoProduct.getDescription())
                    .withName(dtoProduct.getName())
                    .withCost(dtoProduct.getCost())
                    .build();
            prji.save(product);
            status = "success";
        } else {
            status = "fail";
        }
        List<DtoProduct> products = new ArrayList<>();
        long num = dtoProduct.getNum();
        long offset = dtoProduct.getOffset();
        List<Product> dbProducts = prji.getList(num, offset);
        for (Product p : dbProducts) {
            products.add(DtoProductBuilder.aDtoProduct()
                    .withName(p.getName())
                    .withCost(p.getCost())
                    .withId(p.getId())
                    .withDescription(p.getDescription())
                    .build());
        }
        return DtoProductsStateBuilder.aDtoProductsState()
                .withProducts(products)
                .withStatus(status)
                .withTotalCount(prji.getCount())
                .build();
    }
}
