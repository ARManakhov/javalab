package dev.sirosh.servlets;

import dev.sirosh.dto.DtoAddressBuilder;
import dev.sirosh.services.AddAddressService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newaddress")
public class NewAddressServlet extends HttpServlet {
    AddAddressService addAddressService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        addAddressService = (AddAddressService) servletContext.getAttribute("addAddressService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description;
        if (req.getParameter("description") != null) {
            description = req.getParameter("description");
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                addAddressService.execute(DtoAddressBuilder.aDtoAddress().withDescription(description).withToken(token).build());
                req.getRequestDispatcher("/").forward(req, resp);
            } else {
                req.setAttribute("err", "auth before make this ");
                req.getRequestDispatcher("/auth").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/address").forward(req, resp);
        }
    }
}
