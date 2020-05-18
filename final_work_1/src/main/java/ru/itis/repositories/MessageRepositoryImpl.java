package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itis.models.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(Message entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Message entity) {

        return false;
    }

    @Override
    public boolean delete(Message entity) {
        return false;
    }

    @Override
    public Optional<Message> findById(Long aLong) {
        Message message = entityManager.find(Message.class, aLong);
        entityManager.detach(message);
        if (message != null) {
            return Optional.of(message);
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
