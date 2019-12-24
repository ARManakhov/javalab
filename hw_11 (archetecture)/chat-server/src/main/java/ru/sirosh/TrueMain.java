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
                RequestsDispatcher dispatcher = RequestsDispatcherBuilder.aRequestsDispatcher()
                        .withLogInService(logInService)
                        .withRegSevice(regSevice)
                        .withSendService(sendService)
                        .withGetProductsService(getProductsService)
                        .withAddProductService(addProductService)
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
