package reposetory;

import jdk.nashorn.internal.scripts.JD;
import model.Course;
import model.Lesson;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CourseRepositoryImpl implements CourseRepository {

    private static final String SAVE_COURSE_QUERY = "insert into course (title) values (?)";
    private static final String SAVE_LESSON_QUERY = "insert into lesson (title,text,course_id) values (?,?,?)";
    private static final String UPDATE_COURSE_QUERY = "update course set title = ?  where id = ? ;";
    private static final String UPDATE_LESSON_QUERY = "update lesson set title = ?, text = ? where id = ?;";
    private static final String DELETE_COURSE_QUERY = "delete  from course where id = ?";
    private static final String DELETE_LESSON_QUERY = "delete  from lesson where id = ?";
    private static final String FIND_ALL_COURSES_QUERY = "select course.*, lesson.title as lesson_title, lesson.text as lesson_text, lesson.id as lesson_id " +
            "from course left join lesson on course.id = lesson.course_id ";
    private static final String FIND_COURSE_QUERY = "select course.*, lesson.title as lesson_title, lesson.text as lesson_text, lesson.id as lesson_id " +
            "from course left join lesson on course.id = lesson.course_id " +
            "where course.id = ?";


    private JdbcTemplate jdbcTemplate;


    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_COURSE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getTitle());

            return statement;
        }, keyHolder);
        for (Lesson lesson : course.getLessons()) {
            jdbcTemplate.update(SAVE_LESSON_QUERY, lesson.getTitle(), lesson.getText(), keyHolder.getKeys().get("id"));
        }
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getTitle(),course.getId());
        for (Lesson lesson : course.getLessons()) {
            jdbcTemplate.update(UPDATE_LESSON_QUERY, lesson.getText(), lesson.getTitle(),lesson.getId());
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_COURSE_QUERY, id);
        jdbcTemplate.update(DELETE_LESSON_QUERY, id);
    }

    @Override
    public Optional<Course> find(Long id) {
        final Optional<Course>[] courseOptional = new Optional[]{Optional.empty()};
        jdbcTemplate.query(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_COURSE_QUERY);
            preparedStatement.setLong(1,id);
            return preparedStatement;
        }, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                long id = resultSet.getLong("id");
                Course course;
                if (courseOptional[0].isPresent()) {
                    course = courseOptional[0].get();
                } else {
                    course = Course.builder()
                            .id(id)
                            .title(resultSet.getString("title"))
                            .lessons(new ArrayList<>())
                            .build();
                    courseOptional[0] = Optional.of(course);
                }
                Lesson lesson = Lesson.builder()
                        .id(resultSet.getLong("lesson_id"))
                        .courseId(id)
                        .title(resultSet.getString("lesson_title"))
                        .text(resultSet.getString("lesson_text"))
                        .build();
                course.getLessons().add(lesson);
            }
        });
        return courseOptional[0];
    }

    @Override
    public List<Course> findAll() {
        Map<Long, Course> courseMap = new HashMap<>();
        jdbcTemplate.query(FIND_ALL_COURSES_QUERY, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                long id = resultSet.getLong("id");
                Course course;
                if (courseMap.get(id)!= null) {
                    course = courseMap.get(id);
                } else {
                    course = Course.builder()
                            .id(id)
                            .title(resultSet.getString("title"))
                            .lessons(new ArrayList<>())
                            .build();
                    courseMap.put(id, course);
                }
                Lesson lesson = Lesson.builder()
                        .id(resultSet.getLong("lesson_id"))
                        .courseId(id)
                        .title(resultSet.getString("lesson_title"))
                        .text(resultSet.getString("lesson_text"))
                        .build();
                course.getLessons().add(lesson);
            }
        });
    return new ArrayList<>(courseMap.values());
    }
}
