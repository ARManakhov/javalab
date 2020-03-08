import model.Course;
import model.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import reposetory.CourseRepository;
import reposetory.CourseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class Save {
    public static void main(String[] args) throws Exception {
        JdbcTemplate jdbcTemplate = Init.init();
        CourseRepository courseRepository = new CourseRepositoryImpl(jdbcTemplate);
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(Lesson.builder().title("test lesson 1").text("text").build());
        lessonList.add(Lesson.builder().title("test lesson 2").text("text").build());
        lessonList.add(Lesson.builder().title("test lesson 3").text("text").build());
        lessonList.add(Lesson.builder().title("test lesson 4").text("text").build());
        Course course = Course.builder().title("test course").lessons(lessonList).build();
        courseRepository.save(course);
    }
}
