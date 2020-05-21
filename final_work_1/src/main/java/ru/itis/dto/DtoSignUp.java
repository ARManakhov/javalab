package ru.itis.dto;

import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoSignUp {
    private String name;
    private String email;
    private String password;
    private String passwordCheck;
}
