package ru.itis.repositories;

import ru.itis.models.Message;

public abstract class MessageRepository  extends CrudRepository<Message,Long> {

    public MessageRepository() {
        super(Message.class);
    }
}
