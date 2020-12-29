package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class UserProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/profile/{id}")
    String getProfile(Model model, @PathVariable Long id) {
        Optional<User> userO = userService.getUser(id);
        if (userO.isPresent()) {
            model.addAttribute("user", userO.get());
            return "profile";
        }else {
            return "redirect:404";
        }
    }
}
