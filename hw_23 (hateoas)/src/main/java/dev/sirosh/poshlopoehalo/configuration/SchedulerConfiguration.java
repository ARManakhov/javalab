package dev.sirosh.poshlopoehalo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;

@Configuration
public class SchedulerConfiguration {
    @Bean
    public TaskScheduler taskScheduler() {

        TaskScheduler scheduler = new ThreadPoolTaskScheduler();

        return scheduler;
    }
}
