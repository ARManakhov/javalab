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

    public static DtoUser from(User user) {
        return DtoUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    public static List<DtoUser> from(List<User> users) {
        return users.stream()
                .map(DtoUser::from)
                .collect(Collectors.toList());
    }

}
