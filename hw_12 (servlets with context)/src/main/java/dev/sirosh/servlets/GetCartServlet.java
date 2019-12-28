package dev.sirosh.servlets;

import dev.sirosh.dto.DtoGetList;
import dev.sirosh.dto.DtoProduct;
import dev.sirosh.dto.DtoProducts;
import dev.sirosh.services.GetCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getcart")
public class GetCartServlet extends HttpServlet {
    GetCartService getCartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getCartService = (GetCartService) servletContext.getAttribute("getCartService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            DtoProducts dtoProducts = ((DtoProducts) getCartService.execute(new DtoGetList(0,0,token)));
            List<DtoProduct> productList = dtoProducts.getProducts();

            req.setAttribute("products", productList);
            req.getRequestDispatcher("/cart.ftl").forward(req, resp);
        }else {
            req.setAttribute("err", "auth before make this ");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}