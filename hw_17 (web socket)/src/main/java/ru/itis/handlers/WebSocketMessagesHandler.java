package ru.itis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.MessageService;

import java.io.IOException;
import java.security.Principal;

import java.sql.Timestamp;
import java.util.*;

@Component("webSocketMessagesHandler")
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    static final Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MessageService messageService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {

        String id = (String) session.getAttributes().get("id");
        String messageText = (String) message.getPayload();
        MessageDto messageDtoFromJson = objectMapper.readValue(messageText, MessageDto.class);
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) session.getPrincipal();
        User user = ((UserDetailsImpl) principal.getPrincipal()).getUser();
        messageDtoFromJson.setAuthorName(user.getName());
        Date date = new Date();
        boolean added = sessions.add(session);
        if (!added) messageService.saveMessage(Message.builder().author(user).text(messageDtoFromJson.getText()).room(Room.builder().id(Long.parseLong(id)).build()).sendTime(new Timestamp(date.getTime())).build());


        for (WebSocketSession currentSession : sessions) {
            if (((String) currentSession.getAttributes().get("id")).equals(id)) {

                currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDtoFromJson)));
            }
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
        sessions.remove(session);
    }
}
