package dev.sirosh.poshlopoehalo.dto;

import lombok.*;

import javax.persistence.*;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DtoMovement {
    Long id;
    Long transportId;
    Long cityFromId;
    Long cityToId;
    Date departureDate;
    Date arrivalDate;
    Double price;
}
