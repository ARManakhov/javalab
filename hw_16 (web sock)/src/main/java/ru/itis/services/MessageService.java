package ru.itis.services;

import ru.itis.models.Message;

import java.util.List;

public interface MessageService {
    boolean saveMessage(Message message);
    List<Message> getLastTem();
}
