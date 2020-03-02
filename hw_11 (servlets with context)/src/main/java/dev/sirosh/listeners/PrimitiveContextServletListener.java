package dev.sirosh.listeners;

import dev.sirosh.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class PrimitiveContextServletListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("starting filling context ...");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Connection dbConnection = null;
        try {
            java.sql.Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            dbConnection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres?currentSchema=public", props);
        } catch (SQLException e) {
            System.out.println("can't connect to database");
            e.printStackTrace();
        }
        servletContext.setAttribute("databaseConnection", dbConnection);
        if (dbConnection != null) {
            AddAddressService addAddressService = new AddAddressService(dbConnection);
            servletContext.setAttribute("addAddressService", addAddressService);
            AddProductCartService addProductCartService = new AddProductCartService(dbConnection);
            servletContext.setAttribute("addProductCartService", addProductCartService);
            AddProductService addProductService = new AddProductService(dbConnection);
            servletContext.setAttribute("addProductService", addProductService);
            DelAddressService delAddressService = new DelAddressService(dbConnection);
            servletContext.setAttribute("delAddressService", delAddressService);
            DeleteProductService deleteProductService = new DeleteProductService(dbConnection);
            servletContext.setAttribute("deleteProductService", deleteProductService);
            DelProductCartService delProductCartService = new DelProductCartService(dbConnection);
            servletContext.setAttribute("delProductCartService",delProductCartService);
            GetAddressesService getAddressesService = new GetAddressesService(dbConnection);
            servletContext.setAttribute("getAddressesService",getAddressesService);
            GetCartService getCartService = new GetCartService(dbConnection);
            servletContext.setAttribute("getCartService",getCartService);
            GetOrdersService getOrdersService =new GetOrdersService(dbConnection);
            servletContext.setAttribute("getOrdersService",getOrdersService);
            GetProductsService getProductsService = new GetProductsService(dbConnection);
            servletContext.setAttribute("getProductsService",getProductsService);
            LogInService logInService = new LogInService(dbConnection);
            servletContext.setAttribute("logInService",logInService);
            NewOrderService newOrderService = new NewOrderService(dbConnection);
            servletContext.setAttribute("newOrderService",newOrderService);
            RegSevice regSevice = new RegSevice(dbConnection);
            servletContext.setAttribute("regSevice",regSevice);
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
