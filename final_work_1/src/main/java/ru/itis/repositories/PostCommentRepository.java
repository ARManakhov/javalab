package ru.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.models.PostComment;

public interface PostCommentRepository extends CrudRepository<PostComment, Long> {
}
