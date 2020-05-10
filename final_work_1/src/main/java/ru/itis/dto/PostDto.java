package ru.itis.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.itis.models.User;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDto {
    long id;
    String text;
    String header;
    User author;
}
