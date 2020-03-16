package dev.sirosh.servlets.servlets;

import dev.sirosh.servlets.services.UserServiceImpl;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import dev.sirosh.servlets.models.User;
import dev.sirosh.servlets.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    UserService userService;
    FreeMarkerConfigurer freeMarkerConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        userService = springContext.getBean(UserService.class);
        freeMarkerConfig = springContext.getBean(FreeMarkerConfigurer.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.register(User.builder().name(req.getParameter("login")).mail(req.getParameter("mail")).password(req.getParameter("password")).build());
        resp.getWriter().println("ok");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Template template = freeMarkerConfig.getConfiguration().getTemplate("register.ftl");
        try {
            template.process(new HashMap<>(), resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
