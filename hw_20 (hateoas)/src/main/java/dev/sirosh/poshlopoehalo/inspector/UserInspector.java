package dev.sirosh.poshlopoehalo.inspector;

import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


public class UserInspector implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal != null) {
                
                try {
                    User user = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) userPrincipal).getPrincipal()).getUser();

                    modelAndView.getModel().put("navUser", user);
                }catch (Exception e){}
            }
        }
    }
}
