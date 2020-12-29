package dev.sirosh.poshlopoehalo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("admin")
    String getAdminPage(){
        return "admin";
    }
}
