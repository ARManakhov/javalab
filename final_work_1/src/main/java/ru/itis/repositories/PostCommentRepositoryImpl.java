package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itis.models.PostComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Repository
public class PostCommentRepositoryImpl implements PostCommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(PostComment entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(PostComment entity) {
        return false;
    }

    @Override
    public boolean delete(PostComment entity) {
        return false;
    }

    @Override
    public Optional<PostComment> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PostComment> findAll() {
        return null;
    }
}
