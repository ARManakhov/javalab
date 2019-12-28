package dev.sirosh.servlets;

import dev.sirosh.dto.*;
import dev.sirosh.services.GetAddressesService;
import dev.sirosh.services.NewOrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/makeOrder")
public class MakeOrderServlet extends HttpServlet {
    GetAddressesService getAddressesService;
    NewOrderService newOrderService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getAddressesService = (GetAddressesService) servletContext.getAttribute("getAddressesService");
        newOrderService = (NewOrderService) servletContext.getAttribute("newOrderService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long addresId ;
        if (req.getParameter("id") != null) {
            addresId = Long.parseLong(req.getParameter("id"));
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                newOrderService.execute(DtoAddressBuilder.aDtoAddress().withId(addresId).withToken(token).build());
                req.getRequestDispatcher("/").forward(req, resp);
            } else {
                req.setAttribute("err", "auth before make this ");
                req.getRequestDispatcher("/auth").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/").forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            DtoAddresses dtoProducts = ((DtoAddresses) getAddressesService.execute(new DtoGetList(0,0,token)));
            List<DtoAddress> productList = dtoProducts.getAddresses();
            req.setAttribute("addresses", productList);
            req.getRequestDispatcher("/makeOrder.ftl").forward(req, resp);
        }else {
            req.setAttribute("err", "auth before make this ");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}
