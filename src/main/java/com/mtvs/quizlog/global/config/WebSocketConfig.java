package com.mtvs.quizlog.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // `/topic` 접두사 : 서버 -> 클라이언트에게 메시지를 전달하는 브로커 경로
        config.enableSimpleBroker("/topic");
        // `/app` 접두사 : 클라이언트 -> 서버의 @MessageMapping 메서드 호출 경로
        config.setApplicationDestinationPrefixes("/app");
    }


}