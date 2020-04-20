package ru.itis.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.security.details.UserDetailsImpl;

import java.util.Collection;

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model, Authentication authentication) {
            model.addAttribute("name",authentication.getName());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        UserDetailsImpl loh = (UserDetailsImpl) authentication.getPrincipal();
        loh.getUser();
        System.out.println(loh.getUsername());
        return "profile";
    }
}
