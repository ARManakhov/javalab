package ru.itis.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.DtoUser;
import ru.itis.services.UsersService;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {

        model.addAttribute("users", usersService.getUsers());
        return "users";
    }


/*    @RequestMapping(value = "/users/{user-id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("user-id") Long userId) {
        usersService.deleteUser(userId);
        return "redirect:/users";
    }*/
}
