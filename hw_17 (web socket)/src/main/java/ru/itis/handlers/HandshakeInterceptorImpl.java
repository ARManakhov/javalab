package ru.itis.handlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.itis.services.RoomService;

import java.util.Map;

@Component
public class HandshakeInterceptorImpl implements HandshakeInterceptor {
    @Autowired
    RoomService roomService;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String[] path = request.getURI().getPath().split("/");
        String id = path[2];
        if (roomService.getRoom(Long.valueOf(id)).isPresent()){
        attributes.put("id", id);
        return true;}else return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Nothing to do after handshake
    }
}
