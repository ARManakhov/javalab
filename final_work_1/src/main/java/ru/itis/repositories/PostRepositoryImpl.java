package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itis.models.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Repository
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(Post entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entityManager);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Post entity) {
        return false;
    }

    @Override
    public boolean delete(Post entity) {
        return false;
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }
}
