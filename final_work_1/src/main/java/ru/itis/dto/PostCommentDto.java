package ru.itis.dto;

import lombok.*;
import ru.itis.models.Post;
import ru.itis.models.User;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostCommentDto {
    String text;
}
