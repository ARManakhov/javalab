package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E,ID> {
    boolean save(E entity);
    boolean update(E entity);
    boolean delete(E entity);
    Optional<E> findById(ID id);
    List<E> findAll();

}
