package ru.itis.repositories;

import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Post;
import ru.itis.models.PostLike;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class PostLikeRepositoryImpl implements PostLikeRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public boolean save(PostLike entity) {
        entityManager.persist(entity);
        return false;
    }

    @Override
    public boolean update(PostLike entity) {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(PostLike entity) {
        entityManager.remove(entity);
        return true;
    }

    @Override
    public Optional<PostLike> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PostLike> findAll() {
        return null;
    }

    @Override
    public Optional<PostLike> findByEntity(PostLike postLike) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PostLike> cr = cb.createQuery(PostLike.class);
        Root<PostLike> root = cr.from(PostLike.class);
        cr.select(root).where(cb.equal(root.get("userId"), postLike.getUser().getId())).where(cb.equal(root.get("postId"), postLike.getPost().getId()));
        TypedQuery<PostLike> query = entityManager.createQuery(cr);
        return Optional.of(query.getSingleResult());
    }
}
