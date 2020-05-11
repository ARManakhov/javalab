package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.services.SignUpService;

@Controller
public class SignInController {
    @Autowired
    private SignUpService service;

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String getSignInPage() {
            return "sign_in";
    }
}
