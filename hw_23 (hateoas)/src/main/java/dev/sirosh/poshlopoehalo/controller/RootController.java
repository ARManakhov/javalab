package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @Autowired
    MovementService movementService;
    @GetMapping("/")
    String getRootPage(Authentication authentication, Model model) {
        model.addAttribute("movements",movementService.getAll());
        return "root";
    }


}
