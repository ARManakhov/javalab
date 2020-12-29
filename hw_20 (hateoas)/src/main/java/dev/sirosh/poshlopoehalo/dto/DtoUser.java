package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DtoUser {
    long id;
    String name;
    String email;
    String password;

    public static DtoUser getDto(User user){
        return DtoUser.builder()
                .id(user.getId())
                .email(user.getMail())
                .name(user.getName())
                .build();
    }
}
