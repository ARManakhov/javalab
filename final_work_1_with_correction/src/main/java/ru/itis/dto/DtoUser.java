package ru.itis.dto;

import lombok.Builder;
import ru.itis.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class DtoUser {
    long id;
    String name;
    String email;
    String password;

    public static DtoUser from(User userSimple) {
        return DtoUser.builder()
                .email(userSimple.getEmail())
                .name(userSimple.getName())
                .id(userSimple.getId())
                .build();
    }

    public static List<DtoUser> from(List<User> userSimples) {
        return userSimples.stream()
                .map(DtoUser::from)
                .collect(Collectors.toList());
    }

    public static DtoUser getDto(User user) {
        return DtoUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
