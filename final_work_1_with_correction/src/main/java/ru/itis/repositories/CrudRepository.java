package ru.itis.repositories;

import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.PostLike;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class CrudRepository<E, ID> {

    private final Class<E> type;

    protected CrudRepository(Class<E> type) {
        this.type = type;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean save(E entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    E update(E entity){
        return entityManager.merge(entity);
    }

    @Transactional
    public boolean delete(E entity) {
        entityManager.remove(entity);
        return true;
    }

    @Transactional
    public Optional<E> findById(ID aLong) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cr = cb.createQuery(type);
        Root<E> root = cr.from(type);
        cr.select(root).where(cb.equal(root.get("id"), aLong));
        TypedQuery<E> query = entityManager.createQuery(cr);
        return Optional.of(query.getSingleResult());
    }

    @Transactional
    public  List<E> findAll(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(type);
        Root<E> rootEntry = cq.from(type);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

}
