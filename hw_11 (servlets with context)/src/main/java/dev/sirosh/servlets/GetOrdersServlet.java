package dev.sirosh.servlets;

import dev.sirosh.dto.*;
import dev.sirosh.services.GetOrdersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class GetOrdersServlet extends HttpServlet {
    GetOrdersService getOrdersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getOrdersService = (GetOrdersService) servletContext.getAttribute("getOrdersService");
        super.init(config);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            DtoOrders dtoOrders = ((DtoOrders) getOrdersService.execute(new DtoGetList(0,0,token)));
            List<DtoOrder> productList = dtoOrders.getOrders();

            req.setAttribute("orders", productList);
            req.getRequestDispatcher("/orders.ftl").forward(req, resp);
        }else {
            req.setAttribute("err", "auth before make this ");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}
