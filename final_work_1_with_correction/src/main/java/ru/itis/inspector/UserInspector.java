package ru.itis.inspector;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Component
public class UserInspector implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal != null) {
                

                    User user = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) userPrincipal).getPrincipal()).getUser();

                   // modelAndView.getModel().put("navUser", user);

            }
        }
    }
}
