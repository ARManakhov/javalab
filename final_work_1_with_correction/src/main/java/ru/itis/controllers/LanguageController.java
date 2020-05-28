package ru.itis.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.dto.DtoPost;
import ru.itis.models.Post;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;

import javax.servlet.http.HttpSession;

@Controller
public class LanguageController {

    @RequestMapping(value = "/language", method = RequestMethod.POST)
    String setLanguage(HttpSession session, String code ) {
        session.setAttribute("language", code);
        return "redirect:/";
    }
}
