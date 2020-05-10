package ru.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.models.Message;

public interface MessageRepository  extends CrudRepository<Message,Long> {

}
