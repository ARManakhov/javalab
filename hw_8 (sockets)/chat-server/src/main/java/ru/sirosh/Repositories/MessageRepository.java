package ru.sirosh.Repositories;


import ru.sirosh.Models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {
    List<Message>  findMessagesInChat(int count);
}
