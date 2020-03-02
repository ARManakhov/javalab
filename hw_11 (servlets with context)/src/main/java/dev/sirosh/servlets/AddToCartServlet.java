package dev.sirosh.servlets;

import dev.sirosh.dto.DtoProductListBuilder;
import dev.sirosh.services.AddProductCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    AddProductCartService addProductCartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        addProductCartService = (AddProductCartService) servletContext.getAttribute("addProductCartService");
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long productId = 0L;
        //User user = null;
        if (req.getParameter("id") != null) {
            productId = Long.parseLong(req.getParameter("id"));
        } else {
            req.getRequestDispatcher("/").forward(req, resp);
        }
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            //user = new TokenizeUser().decodeJwt(token);
            addProductCartService.execute(DtoProductListBuilder.aDtoProductList().withId(productId).withToken(token).build());
            req.getRequestDispatcher("/").forward(req, resp);
        } else {
            req.setAttribute("err", "auth before make this ");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}
