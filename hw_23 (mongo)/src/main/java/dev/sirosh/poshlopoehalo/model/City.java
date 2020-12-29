package dev.sirosh.poshlopoehalo.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City  {
    @Id
    Long _id;
    String name;
    Double cordX;
    Double cordY;
}
