package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.dto.DtoCity;
import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CityController {
    @Autowired
    CityService cityService;
    @GetMapping("/new_city")
    String newCityPage() {
        return "new_city";
    }

    @PostMapping("/new_city")
    String newCityMethod( DtoCity dtoCity) {
        City city = City.builder()
                .name(dtoCity.getName())
                .cordX(dtoCity.getCordX())
                .cordY(dtoCity.getCordY())
                .build();
        cityService.add(city);
        return "redirect:/new_city";
    }


}
