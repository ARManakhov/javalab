package ru.itis.dto;

import lombok.*;
import ru.itis.models.Post;
import ru.itis.models.User;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DtoPostComment {
    @Size(min = 3, max = 256)
    String text;
}
