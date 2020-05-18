package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSignUpController {
    @Autowired
    private SignUpService service;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(SignUpDto form) {
        service.signUp(form);
        return "redirect:/api/signIn";
    }
}
