package ru.itis.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.models.Subscription;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public boolean save(Subscription entity) {
        entityManager.persist(entity);
        return false;
    }

    @Override
    public boolean update(Subscription entity) {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Subscription entity) {
        entityManager.remove(entity);
        return true;
    }

    @Override
    public Optional<Subscription> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public Optional<Subscription> findByEntity(Subscription subscription) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> cr = cb.createQuery(Subscription.class);
        Root<Subscription> root = cr.from(Subscription.class);
        cr.select(root).where(cb.equal(root.get("author"), subscription.getAuthor().getId())).where(cb.equal(root.get("subscriber"), subscription.getSubscriber().getId()));
        TypedQuery<Subscription> query = entityManager.createQuery(cr);
        return Optional.of(query.getSingleResult());
    }
}
