package dev.sirosh.poshlopoehalo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PoshlopoehaloApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoshlopoehaloApplication.class, args);
    }

}
