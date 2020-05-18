package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.UsersService;

import java.util.Optional;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUsersController {

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
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> fullUserO = usersService.getUser(userDetails.getUser().getId());
        if (fullUserO.isPresent()) {
            model.addAttribute("user",fullUserO.get());
            return "profile";
        } else {
            return "redirect:404";
        }
    }


}
