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
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    TransportType transportType;
    String description;
    String model;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "transport")
    List<Movement> fromMovements;
}
