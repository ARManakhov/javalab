package ru.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.itis.models.Message;
import ru.itis.models.Room;

public interface RoomRepository extends CrudRepository<Room,Long> {
}
