package ru.itis.repositories;

import org.springframework.stereotype.Repository;
import ru.itis.models.User;

import java.util.Optional;

@Repository
public abstract class UserRepository extends CrudRepository<User,Long> {
    public UserRepository() {
        super(User.class);
    }

    abstract Optional<User> findUserByEmail(String email);
   public abstract Optional<User> findUserByName(String email);

}
