package ru.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
