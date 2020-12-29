package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class City  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Double cordX;
    Double cordY;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "from")
    List<Movement> fromMovements;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "to")
    List<Movement> toMovements;
}
