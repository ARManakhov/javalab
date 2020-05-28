package ru.itis.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.itis.models.User;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DtoPost {
    long id;
    @Size(min = 3, max = 1024)
    String text;
    @Size(min = 3, max = 256)
    String header;

    User author;
}
