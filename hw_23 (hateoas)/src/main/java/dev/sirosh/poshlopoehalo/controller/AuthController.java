package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.dto.DtoSignUp;
import dev.sirosh.poshlopoehalo.model.ValidationToken;
import dev.sirosh.poshlopoehalo.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    SignUpService signUpService;

    @GetMapping("/validate")
    String validate(String token){
        if (signUpService.mailSignInValidate(token)){
            return "redirect:/";
        } else {
            return "redirect:/signIn";
        }

    }

    @PostMapping("/signInByMail")
    String initValidation(String mail){
        signUpService.mailSignInSend(mail, new ValidationToken());
        return "redirect:/";
    }
    @GetMapping("/sign_in_mail")
    String signInMailPage(Model model) {
        return "sign_in_mail";
    }


    @GetMapping("/sign_up")
    String signUpPage(Model model) {
        return "sign_up";
    }

    @GetMapping("/sign_in")
    String signInPage(Model model) {
        return "sign_in";
    }

    @PostMapping("/sign_up")
    String signUpMethod(Model model, DtoSignUp dtoSignUp) {
        if (dtoSignUp.getPassword().equals(dtoSignUp.getPasswordCheck())) {
            signUpService.signUp(dtoSignUp);
            return "redirect:/";
        }else {
            return "redirect:/sign_up";
        }
    }
}
