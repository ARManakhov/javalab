package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.UsersService;

import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {

        model.addAttribute("users", usersService.getUsers());
        return "users";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User userSimple = userDetails.getUser();

        return "redirect:/profile/" + userSimple.getId();
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String getProfile(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            model.addAttribute("navUser", userDetails.getUser());
        }
        Optional<User> fullUserO = usersService.getUser(id);
        if (fullUserO.isPresent()) {
            User user = fullUserO.get();
            model.addAttribute("user", user);
            return "profile";
        } else {
            return "redirect:404";
        }
    }


}
