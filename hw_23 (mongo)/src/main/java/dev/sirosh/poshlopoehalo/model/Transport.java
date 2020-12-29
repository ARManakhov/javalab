package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Transport {
    Long id;
    TransportType transportType;
    String description;
    String model;
    List<Movement> fromMovements;
}
