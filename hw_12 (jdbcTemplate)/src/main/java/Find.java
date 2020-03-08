import model.Course;
import model.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import reposetory.CourseRepository;
import reposetory.CourseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class Find {
    public static void main(String[] args) throws Exception {
        JdbcTemplate jdbcTemplate = Init.init();
        CourseRepository courseRepository = new CourseRepositoryImpl(jdbcTemplate);
        System.out.println(courseRepository.find(13L));
        System.out.println(courseRepository.findAll());
    }
}
