package dev.sirosh.servlets;

import dev.sirosh.dto.DtoUser;
import dev.sirosh.dto.DtoUserBuilder;
import dev.sirosh.services.RegSevice;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private RegSevice regSevice;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        regSevice = (RegSevice) servletContext.getAttribute("regSevice");
        super.init(config);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DtoUser dtoUser = DtoUserBuilder.aDtoUser()
                .withUsername(req.getParameter("username"))
                .withPassword(req.getParameter("password"))
                .build();
        DtoUser authUser = (DtoUser) regSevice.execute(dtoUser);
        HttpSession currentSession = req.getSession();
        if (authUser != null && authUser.getToken() != null) {
            currentSession.setAttribute("token", authUser.getToken());
            resp.sendRedirect("/");
        } else {
            req.setAttribute("err", "username already exist");
            req.getRequestDispatcher("/auth").forward(req, resp);
        }
    }
}