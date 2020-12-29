package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.repository.CityRepository;
import dev.sirosh.poshlopoehalo.repository.MovementRepository;
import dev.sirosh.poshlopoehalo.repository.RepositoriesConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Repository {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
        CityRepository cityRepository = context.getBean(CityRepository.class);
        MovementRepository movementRepository = context.getBean(MovementRepository.class);

        City samaraCity = City.builder().name("Samara")
                .cordX(-5D)
                .cordY(-20D)
                .build();

        City saratovCity = City.builder().name("Saratov")
                .cordX(-50D)
                .cordY(-20D)
                .build();

        cityRepository.save(samaraCity);
        cityRepository.save(saratovCity);

        Movement saratovToSamara = Movement.builder()
                .from(saratovCity)
                .to(samaraCity)
                .price(1234D)
                .arrivalDate(new Date())
                .departureDate(new Date())
                .transport(Transport.builder()
                        .model("жигуль")
                        .build())
                .build();

        movementRepository.save(saratovToSamara);
        System.out.println(movementRepository.findAll());
    }
}
