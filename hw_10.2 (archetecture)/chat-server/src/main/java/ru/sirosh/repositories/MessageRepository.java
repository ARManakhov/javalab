package ru.sirosh.repositories;


import ru.sirosh.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {
    List<Message>  findMessagesInChat(int count);

    List<Message> findLastMessageWithOffset(long last, long offset);
}
