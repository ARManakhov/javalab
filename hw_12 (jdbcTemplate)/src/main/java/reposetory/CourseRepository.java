package reposetory;

import model.Course;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Long, Course> {
    Optional<Course> find(Long id);
}
