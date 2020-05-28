package ru.itis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({JdbcTemplateConfig.class, EntityManagerConfig.class,SecurityConfig.class,ViewConfig.class})
public class ApplicationContextConfig {
    @Autowired
    private Environment environment;

}
