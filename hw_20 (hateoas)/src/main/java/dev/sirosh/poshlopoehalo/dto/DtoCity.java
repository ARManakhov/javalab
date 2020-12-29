package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.City;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DtoCity {
    Long id;
    String name;

    Double cordX;

    Double cordY;

    public static DtoCity toDto(City city) {
        return DtoCity.builder()
                .id(city.getId())
                .name(city.getName())
                .cordX(city.getCordX())
                .cordY(city.getCordY())
                .build();
    }

    public static City toObj(DtoCity dtoCity) {
        return City.builder()
                .id(dtoCity.getId())
                .name(dtoCity.getName())
                .cordX(dtoCity.getCordX())
                .cordY(dtoCity.getCordY())
                .build();
    }
}
