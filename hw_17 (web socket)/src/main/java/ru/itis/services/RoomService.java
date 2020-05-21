package ru.itis.services;

import org.springframework.stereotype.Service;
import ru.itis.models.Room;

import java.util.Optional;

@Service
public interface RoomService {
    Optional<Room> getRoom(Long id);
    boolean newRoom(Room room);
}
