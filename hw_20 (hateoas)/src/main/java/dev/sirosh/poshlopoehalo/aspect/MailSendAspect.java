package dev.sirosh.poshlopoehalo.aspect;

import dev.sirosh.poshlopoehalo.dto.DtoMovementExt;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.ValidationToken;
import dev.sirosh.poshlopoehalo.service.MailService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class MailSendAspect {

    @Autowired
    MailService mailService;

    @After("execution(* dev.sirosh.poshlopoehalo.service.SignUpServiceImpl.mailSignInSend(..)) && args(mail,token,..)")
    public  void afterNewMovement(String mail, ValidationToken token){
        mailService.sendTokenMail(token.getToken(),mail);

    }
}
