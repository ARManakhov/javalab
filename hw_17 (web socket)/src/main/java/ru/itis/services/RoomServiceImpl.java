package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Room;
import ru.itis.repositories.RoomRepository;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Override
    public Optional<Room> getRoom(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public boolean newRoom(Room room) {
        roomRepository.save(room);
        return true;
    }
}
