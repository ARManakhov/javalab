package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Order;
import ru.sirosh.models.Product;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.OrderRepositoryJdbcImpl;
import ru.sirosh.repositories.ProductRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetOrdersService implements Component {

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
        return "get_order";
    }

    @Override
    public Dto execute(Request req) {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(dbConnection);
        User user = new TokenizeUser().decodeJwt(((DtoGetList)req.getData()).getToken());
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