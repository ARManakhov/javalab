package ru.sirosh.services;

import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Message;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.MessageRepositoryJdbcImpl;
import ru.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class GetMessagesService implements Component {

    SocketsManager socketsManager;
    Connection dbConnection;


    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void setSocketsManager(SocketsManager socketsManager) {
        this.socketsManager = socketsManager;
    }

    @Override
    public String getComponentName() {
        return "messages";
    }

    @Override
    public Dto execute(Request req) {
        MessageRepositoryJdbcImpl mrji = new MessageRepositoryJdbcImpl(dbConnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
        List<DtoMessage> messages = new ArrayList<>();
        List<Message> dbmessages = mrji.findLastMessageWithOffset(((DtoGetList) req.getData()).getNum(), ((DtoGetList) req.getData()).getOffset());
        for (Message message : dbmessages) {
            String uname;
            try {
                uname = urji.findOneById(message.getSenderId()).get().getUsername();
            } catch (NoSuchElementException e){
                uname = "anon";
            }
            messages.add(DtoMessageBuilder.aDtoMessage()
                    .withId(message.getId())
                    .withText(message.getText())
                    .withTimestamp(message.getTimestamp())
                    .withUsername(uname)
                    .build());
        }
        return new DtoMessages(messages, mrji.getCount());
    }
}
