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

public class DeleteProductService {

    Connection dbConnection;


    public DeleteProductService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoProductList dtoProductList) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
        DtoProduct dtoProduct = (DtoProduct) dtoProductList;
        User reqUser = new TokenizeUser().decodeJwt(dtoProduct.getToken());
        User user = urji.findOneById(reqUser.getId()).get();
        String status = null;
        if (user.getRole().equals("admin")) {
            Product product = ProductBuilder.aProduct()
                    .withId(dtoProduct.getId())
                    .isDeleited(true)
                    .build();
            prji.delete(product);
            status = "success";
        } else {
            status = "fail";
        }
        List<DtoProduct> products = new ArrayList<>();
        long num = dtoProductList.getNum();
        long offset = dtoProductList.getOffset();
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
