package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.Dto;
import ru.sirosh.dto.DtoMessage;
import ru.sirosh.dto.DtoStatus;
import ru.sirosh.models.MessageBuilder;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketHandler;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.repositories.MessageRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class SendService implements Component {

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
        return "sender";
    }

    @Override
    public Dto execute(Request req) {
        DtoMessage dtoMessage = (DtoMessage) req.getData();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        User user = new TokenizeUser().decodeJwt(dtoMessage.getToken());
        new MessageRepositoryJdbcImpl(dbConnection).save(MessageBuilder.aMessage().withSenderId(user.getId()).withText (dtoMessage.getText()).withTimestamp(currentTime).build());
        dtoMessage.setUsername(user.getUsername());
        dtoMessage.setTimestamp(currentTime);
        socketsManager.sendEveryone(new Request<DtoMessage>("new_message", dtoMessage));
        return new DtoStatus("success");
    }
}
