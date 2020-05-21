package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class DtoSignUp {
    private String name;
    private String email;
    private String password;
    private String passwordCheck;
}
