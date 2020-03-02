package dev.sirosh.servlets;

import dev.sirosh.dto.DtoAddressBuilder;
import dev.sirosh.services.DelAddressService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteaddress")
public class AddressDeleteServlet extends HttpServlet {
    DelAddressService delAddressService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        delAddressService = (DelAddressService) servletContext.getAttribute("delAddressService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long addresId ;
        if (req.getParameter("id") != null) {
            addresId = Long.parseLong(req.getParameter("id"));
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                delAddressService.execute(DtoAddressBuilder.aDtoAddress().withId(addresId).withToken(token).build());
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
