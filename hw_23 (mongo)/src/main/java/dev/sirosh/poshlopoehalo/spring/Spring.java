package dev.sirosh.poshlopoehalo.spring;


import dev.sirosh.poshlopoehalo.model.Movement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class Spring {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SimpleMongoConfig.class);
        MongoTemplate template = context.getBean(MongoTemplate.class);

        List<Movement> movements = template.find(new Query(
                where("price").lt(3000)), Movement.class, "movement");
        System.out.println(movements);

    }
}
