package dev.sirosh.poshlopoehalo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DtoSignUp {
    String username;
    String mail;
    String password;
    String passwordCheck;
}
