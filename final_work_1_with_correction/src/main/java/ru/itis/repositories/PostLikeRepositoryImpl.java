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

public class PostLikeRepositoryImpl extends PostLikeRepository {
    @PersistenceContext
    private EntityManager entityManager;





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
