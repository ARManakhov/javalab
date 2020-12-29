package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_id")
    Transport transport;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_from")
    City from;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_to")
    City to;
    Timestamp departureDate;
    Timestamp arrivalDate;
    Double price;
    Boolean expired;
}
