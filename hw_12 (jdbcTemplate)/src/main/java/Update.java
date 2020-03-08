import model.Course;
import model.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import reposetory.CourseRepository;
import reposetory.CourseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class Update {
    public static void main(String[] args) throws Exception {
        JdbcTemplate jdbcTemplate = Init.init();
        CourseRepository courseRepository = new CourseRepositoryImpl(jdbcTemplate);
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(Lesson.builder().title("test lesson 1 upd").text("text").id(26).build());
        lessonList.add(Lesson.builder().title("test lesson 2 upd").text("text").id(27).build());
        lessonList.add(Lesson.builder().title("test lesson 3 upd").text("text").id(28).build());
        lessonList.add(Lesson.builder().title("test lesson 4 upd").text("text").id(29).build());
        Course course = Course.builder().title("test course upd").lessons(lessonList).id(14).build();
        courseRepository.update(course);
    }
}
