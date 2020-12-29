package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoCity;
import dev.sirosh.poshlopoehalo.dto.DtoCityResponse;
import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.service.CityService;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestCityController {
    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;

    @GetMapping("/api/city/{id}")
    ResponseEntity getCity(@PathVariable Long id) {
        Optional<City> city = cityService.get(id);
        if (city.isPresent()) {
            return ok(EntityModel.of(DtoCityResponse.builder()
                    .status("ok")
                    .dtoCity(DtoCity.toDto(city.get()))
                    .build()));
        }
        return ok(EntityModel.of(DtoCityResponse.builder()
                .status("err")
                .build()));
    }

    @GetMapping("/api/cityName/{name}")
    ResponseEntity getCity(@PathVariable String name) {
        City city = cityService.get(name);
        Map<Object, Object> model = new HashMap<>();
        return ok(EntityModel.of(DtoCityResponse.builder()
                .status("ok")
                .dtoCity(DtoCity.toDto(city))
                .build()));
    }


    @PostMapping("/api/city")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity addCity(DtoCity dtoCity) {
        Map<Object, Object> model = new HashMap<>();

        City city = City.builder()
                .name(dtoCity.getName())
                .cordX(dtoCity.getCordX())
                .cordY(dtoCity.getCordY())
                .build();
        cityService.add(city);
        return ok(EntityModel.of(DtoCityResponse.builder()
                .status("ok")
                .dtoCity(DtoCity.toDto(city))
                .build()));
    }


}
