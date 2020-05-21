package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.MessageService;
import ru.itis.services.RoomService;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    MessageService messageService;

    @Autowired
    RoomService roomService;

    @RequestMapping(value = "/chat/{id}", method = RequestMethod.GET)
    public String getChatPage(Model model, Authentication authentication, @PathVariable long id) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (roomService.getRoom(id).isPresent()) {
            model.addAttribute("user", user);
            model.addAttribute("id", id);
            model.addAttribute("history", messageService.getLastTem(id));
            return "chat";
        } else
            return "redirect:/404";
    }

    @RequestMapping(value = "/chat_new/{id}", method = RequestMethod.GET)
    public String newChatPage(Model model, Authentication authentication, @PathVariable long id) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        roomService.newRoom(Room.builder().id(id).build());
            return "redirect:/chat/"+id;
    }

}
