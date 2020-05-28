package ru.itis.repositories;

import ru.itis.models.PostComment;

public abstract class PostCommentRepository extends CrudRepository<PostComment, Long> {
    public PostCommentRepository() {
        super(PostComment.class);
    }
}
