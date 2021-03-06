package dev.sirosh.servlets;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.DtoGetList;
import dev.sirosh.dto.DtoProduct;
import dev.sirosh.dto.DtoProducts;
import dev.sirosh.models.User;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long page = 0L;
        String username = "";
        boolean isAdmin = false;
        if (req.getParameter("page") != null) {
            page = Long.parseLong(req.getParameter("page"));
        }
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            User user = new TokenizeUser().decodeJwt(token);
            username = user.getUsername();
            isAdmin = user.getRole().equals("admin");
        }

        DtoProducts dtoProducts = ((DtoProducts) getProductsService.execute(new DtoGetList(page * 5, 5)));
        List<DtoProduct> productList = dtoProducts.getProducts();
        long maxPage = (dtoProducts.getTotalCount() / 5) + 1;
        req.setAttribute("isAdmin", isAdmin);
        req.setAttribute("prevPage", page == 0 ? 0 : page - 1);
        req.setAttribute("nextPage", page  <= maxPage ?  page : maxPage);
        req.setAttribute("username", username);
        req.setAttribute("products", productList);
        req.setAttribute("curentPage", ++page);
        req.setAttribute("maxPage", maxPage);
        req.getRequestDispatcher("/index.ftl").forward(req, resp);
    }
}
