package ru.sirosh;

import javax.servlet.*;
import java.io.IOException;

public class ServletFilter implements Filter {
    Logger logger = Logger.getInstance();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.newlog(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
