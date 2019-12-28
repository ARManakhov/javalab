package dev.sirosh.servlets;

import dev.sirosh.dto.DtoGetList;
import dev.sirosh.dto.DtoProduct;
import dev.sirosh.dto.DtoProducts;
import dev.sirosh.services.GetProductsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class MainPageServlet extends HttpServlet {
    private GetProductsService getProductsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getProductsService = (GetProductsService) servletContext.getAttribute("getProductsService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long page = 0L;
        if (req.getParameter("page") != null) {
            page = Long.parseLong(req.getParameter("page"));
        }
        DtoProducts dtoProducts= ((DtoProducts) getProductsService.execute(new DtoGetList(page * 5, 5)));
        List<DtoProduct> productList = dtoProducts.getProducts();
        req.setAttribute("products", productList);
        req.setAttribute("curentPage",page);
        req.setAttribute("maxPage", dtoProducts.getTotalCount()/5);
        req.getRequestDispatcher("/index.ftl").forward(req, resp);
    }
}
