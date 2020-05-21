package ru.itis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
public class WebSocketConfiguration {

    @Autowired
    @Qualifier("webSocketMessagesHandler")
    private TextWebSocketHandler webSocketMessagesHandler;

    @Autowired
    @Qualifier("authHandshakeHandler")
    private HandshakeHandler authHandshakeHandler;

    @Autowired
    HandshakeInterceptor handshakeInterceptor;

    @Bean
    WebSocketConfigurer webSocketConfigurer() {
        return new WebSocketConfigurer() {
            @Override
            public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
                webSocketHandlerRegistry.addHandler(webSocketMessagesHandler, "/chat_ws/{id}")
                        .addInterceptors(handshakeInterceptor)
                        .setHandshakeHandler(authHandshakeHandler).withSockJS();
            }
        };
    }
}
