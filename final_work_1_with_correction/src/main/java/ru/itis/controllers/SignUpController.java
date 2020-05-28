package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.dto.DtoSignUp;
import ru.itis.services.SignUpService;

import javax.validation.Valid;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService service;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String getSignUpPage(Authentication authentication) {
        if (authentication == null) {
            return "sign_up";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid DtoSignUp form) {
        service.signUp(form);
        return "redirect:/signIn";
    }
}
