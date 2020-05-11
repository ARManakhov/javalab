package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.MessageService;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String getChatPage(Model model, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        model.addAttribute("user",user);
        model.addAttribute("history",messageService.getLastTem());
        return "chat";
    }
}
