package ru.sirosh.services;

import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Product;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.ProductRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetProductsService implements Component {

    SocketsManager socketsManager;
    Connection dbConnection;


    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void setSocketsManager(SocketsManager socketsManager) {
        this.socketsManager = socketsManager;
    }

    @Override
    public String getComponentName() {
        return "products";
    }

    @Override
    public Dto execute(Request req) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        List<DtoProduct> products = new ArrayList<>();
        List<Product> dbProducts = prji.getList(((DtoGetList) req.getData()).getOffset(), ((DtoGetList) req.getData()).getNum());
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
