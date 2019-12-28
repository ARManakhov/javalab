package dev.sirosh.servlets;

import dev.sirosh.dto.DtoProductListBuilder;
import dev.sirosh.services.AddProductService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newproduct")
public class NewProductServlet extends HttpServlet {
    AddProductService addProductService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        addProductService = (AddProductService) servletContext.getAttribute("addProductService");
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("name") != null && req.getParameter("description") != null && req.getParameter("cost") != null) {
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                addProductService.execute(DtoProductListBuilder.aDtoProductList().withCost(Double.parseDouble(req.getParameter("cost"))).withName(req.getParameter("name")).withDescription(req.getParameter("description")).withToken(token).build());
                req.getRequestDispatcher("/").forward(req, resp);
            } else {
                req.setAttribute("err", "auth before make this ");
                req.getRequestDispatcher("/auth").forward(req, resp);
            }
        }
    }
}
