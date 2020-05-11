package ru.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.itis.models.Message;

import java.util.List;

public interface MessageRepository  extends CrudRepository<Message,Long> {
    Page<Message> findAll(Pageable pageable);
}
