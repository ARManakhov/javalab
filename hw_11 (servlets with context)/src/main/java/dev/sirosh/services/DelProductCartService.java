package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.Dto;
import dev.sirosh.dto.DtoProduct;
import dev.sirosh.dto.DtoProductBuilder;
import dev.sirosh.dto.DtoProductsStateBuilder;
import dev.sirosh.models.Cart;
import dev.sirosh.models.Product;
import dev.sirosh.models.ProductBuilder;
import dev.sirosh.models.User;
import dev.sirosh.repositories.CartRepositoryJdbcImpl;
import dev.sirosh.repositories.ProductRepositoryJdbcImpl;
import dev.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DelProductCartService {

    Connection dbConnection;


    public DelProductCartService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoProduct dtoProduct) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        User reqUser = new TokenizeUser().decodeJwt(dtoProduct.getToken());
        User user = urji.findOneById(reqUser.getId()).get();
        crji.delProduct(crji.getCartByUserId(user.getId()), ProductBuilder.aProduct().withId(dtoProduct.getId()).build());
        List<DtoProduct> products = new ArrayList<>();
        Cart cart = crji.getCartByUserId(user.getId());
        List<Product> dbProducts = cart.getProductList();
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
                .withStatus("success")
                .withTotalCount(prji.getCount())
                .build();
    }
}
