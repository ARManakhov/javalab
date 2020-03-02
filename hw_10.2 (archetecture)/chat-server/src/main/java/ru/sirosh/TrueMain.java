package ru.sirosh;

import ru.sirosh.configs.ArgumentProperty;
import ru.sirosh.configs.ArgumentReader;
import ru.sirosh.configs.DbPropReader;
import ru.sirosh.configs.DbProperty;
import ru.sirosh.context.ApplicationContext;
import ru.sirosh.context.ApplicationContextReflectionBased;
import ru.sirosh.context.Component;
import ru.sirosh.database.DBConnection;
import ru.sirosh.dispatcher.RequestsDispatcher;
import ru.sirosh.dispatcher.RequestsDispatcherBuilder;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.RequestsHandler;
import ru.sirosh.services.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TrueMain {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextReflectionBased();
        LogInService logInService = (LogInService) context.getComponent(Component.class, "logInService");
        RegSevice regSevice = (RegSevice) context.getComponent(Component.class, "regService");
        SendService sendService = (SendService) context.getComponent(Component.class, "sender");
        GetProductsService getProductsService = (GetProductsService) context.getComponent(Component.class, "products");
        AddProductService addProductService = (AddProductService) context.getComponent(Component.class,"add_product");
        AddProductCartService addProductCartService = (AddProductCartService) context.getComponent(Component.class, "add_to_cart_product");
        DelProductCartService delCartProductService = (DelProductCartService) context.getComponent(Component.class, "del_to_cart_product");
        GetCartService getCartService = (GetCartService ) context.getComponent(Component.class, "get_cart");
        GetAddressesService getAddressesService = (GetAddressesService) context.getComponent(Component.class, "get_addresses");
        DelAddressService delAddressService = (DelAddressService) context.getComponent(Component.class, "del_address");
        AddAddressService addAddressService = (AddAddressService) context.getComponent(Component.class, "add_address");
        NewOrderService newOrderService = (NewOrderService) context.getComponent(Component.class, "new_order");
        GetOrdersService getOrdersService = (GetOrdersService) context.getComponent(Component.class, "get_order");
        GetMessagesService getMessagesService = (GetMessagesService) context.getComponent(Component.class, "messages");
        DeleteProductService deleteProductService = (DeleteProductService) context.getComponent(Component.class, "del_product");
        try {
            ArgumentProperty argumentProperty = new ArgumentReader(args).getProperties();
            try {
                DbProperty dbProperty = new DbPropReader(argumentProperty.getDbPropPath()).read();
                Connection sqlConnection = new DBConnection(dbProperty).connect();
                logInService.setDbConnection(sqlConnection);
                regSevice.setDbConnection(sqlConnection);
                sendService.setDbConnection(sqlConnection);
                getProductsService.setDbConnection(sqlConnection);
                addProductService.setDbConnection(sqlConnection);
                addProductCartService.setDbConnection(sqlConnection);
                getCartService.setDbConnection(sqlConnection);
                delCartProductService.setDbConnection(sqlConnection);
                getAddressesService.setDbConnection(sqlConnection);
                delAddressService.setDbConnection(sqlConnection);
                addAddressService.setDbConnection(sqlConnection);
                newOrderService.setDbConnection(sqlConnection);
                getOrdersService.setDbConnection(sqlConnection);
                getMessagesService.setDbConnection(sqlConnection);
                deleteProductService.setDbConnection(sqlConnection);
                RequestsDispatcher dispatcher = RequestsDispatcherBuilder.aRequestsDispatcher()
                        .withLogInService(logInService)
                        .withRegSevice(regSevice)
                        .withSendService(sendService)
                        .withGetProductsService(getProductsService)
                        .withAddProductService(addProductService)
                        .withAddProductCartService(addProductCartService)
                        .withDelProductCartService(delCartProductService)
                        .withGetCartService(getCartService)
                        .withGetAddressesService(getAddressesService)
                        .withDelAddressService(delAddressService)
                        .withAddAddressService(addAddressService)
                        .withNewOrderService(newOrderService)
                        .withGetOrdersService(getOrdersService)
                        .withGetMessagesService(getMessagesService)
                        .withDeleteProductService(deleteProductService)
                        .build();
                RequestsHandler requestsHandler = new RequestsHandler(dispatcher);

                try {
                    SocketsManager socketsManager = new SocketsManager(requestsHandler,Integer.parseInt(argumentProperty.getServerPort()));
                    sendService.setSocketsManager(socketsManager);
                    socketsManager.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.out.println("wrong path to db properties file, exiting");
            } catch (IllegalArgumentException e){
                System.out.println("wrong properties, exiting");
            } catch (SQLException e) {
                System.out.println("can't connect to database");
            }
        }catch (IllegalArgumentException e){
            System.out.println("wrong arguments, exiting");
        }


    }
}
