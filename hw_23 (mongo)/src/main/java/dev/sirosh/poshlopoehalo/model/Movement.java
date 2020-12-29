package dev.sirosh.poshlopoehalo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Document(collection = "movement")
public class Movement {
    @Id
    String _id;
    Transport transport;
    @DBRef
    City from;
    @DBRef
    City to;
    Timestamp departureDate;
    Timestamp arrivalDate;
    Double price;
    Boolean expired;
}
