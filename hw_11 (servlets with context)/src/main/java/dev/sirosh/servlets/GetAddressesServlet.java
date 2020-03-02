package dev.sirosh.servlets;

import dev.sirosh.dto.DtoAddress;
import dev.sirosh.dto.DtoAddresses;
import dev.sirosh.dto.DtoGetList;
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

@WebServlet("/addresses")
public class GetAddressesServlet extends HttpServlet {

    GetAddressesService getAddressesService;
    NewOrderService newOrderService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getAddressesService = (GetAddressesService) servletContext.getAttribute("getAddressesService");
        newOrderService = (NewOrderService) servletContext.getAttribute("newOrderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            DtoAddresses dtoProducts = ((DtoAddresses) getAddressesService.execute(new DtoGetList(0,0,token)));
            List<DtoAddress> productList = dtoProducts.getAddresses();
            req.setAttribute("addresses", productList);
            req.getRequestDispatcher("/addresses.ftl").forward(req, resp);
        }else {
            req.setAttribute("err", "auth before make this ");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}
