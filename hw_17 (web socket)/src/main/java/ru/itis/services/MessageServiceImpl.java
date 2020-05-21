package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.models.Message;
import ru.itis.models.Room;
import ru.itis.repositories.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl  implements MessageService{

    @Autowired
    MessageRepository messageRepository;

    @Override
    public boolean saveMessage(Message message) {
        try {
            messageRepository.save(message);
            return true;
        }   catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Message> getLastTem() {
        return messageRepository.findAll(PageRequest.of(0,10,Sort.Direction.DESC, "id")).toList();
    }

    @Override
    public List<Message> getLastTem(Long id) {
        return messageRepository.findAllByRoom(PageRequest.of(0,10,Sort.Direction.DESC, "id"), Room.builder().id(id).build()).toList();
    }
}
