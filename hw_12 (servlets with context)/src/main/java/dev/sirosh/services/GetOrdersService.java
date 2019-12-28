package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.*;
import dev.sirosh.models.Order;
import dev.sirosh.models.Product;
import dev.sirosh.models.User;
import dev.sirosh.repositories.OrderRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetOrdersService {
    Connection dbConnection;


    public GetOrdersService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoGetList dtoGetList) {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(dbConnection);
        User user = new TokenizeUser().decodeJwt(dtoGetList.getToken());
        List<Order> orders = orji.getOrderByUserId(user.getId());
        List<DtoOrder> orderList = new ArrayList<>();
        DtoOrders dtoOrders = new DtoOrders(orderList);
        for (Order order : orders) {
            DtoOrder cOrder = DtoOrderBuilder.aDtoOrder()
                    .withId(order.getId())
                    .withDtoAddress(DtoAddressBuilder.aDtoAddress()
                            .withId(order.getAddress().getId())
                            .withDescription(order.getAddress().getDescription())
                            .build())
                    .withStatus(order.getStatus()).build();
            List<DtoProduct> products = new ArrayList<>();
            List<Product> dbProducts = order.getProductList();
            for (Product p : dbProducts) {
                products.add(DtoProductBuilder.aDtoProduct()
                        .withName(p.getName())
                        .withCost(p.getCost())
                        .withId(p.getId())
                        .withDescription(p.getDescription())
                        .build());
            }
            cOrder.setDtoProductList(products);
            orderList.add(cOrder);
        }
        return dtoOrders;
    }
}