package reposetory;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, T> {
    void save(T object);
    void update(T object);
    void delete(ID id);
    List<T> findAll();
}