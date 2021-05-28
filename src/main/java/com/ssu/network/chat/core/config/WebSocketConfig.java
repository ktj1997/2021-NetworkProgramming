package com.ssu.network.chat.core.config;

import com.ssu.network.chat.core.security.JwtProvider;
import com.ssu.network.chat.core.security.exception.NotBearerTokenException;
import com.ssu.network.chat.socket.dao.ChatRepository;
import com.ssu.network.chat.socket.exception.NotLoginException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtProvider jwtProvider;
    private final ChatRepository chatRepository;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
                .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                try {
                    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                    String token = accessor.getFirstNativeHeader("Authorization");
                    if (token != null) {
                        token = jwtProvider.checkingAndParsingBearer(token);
                        Claims claims = jwtProvider.getClaimsFromToken(token);
                        String userName = jwtProvider.getUserNameFromClaims(claims);
                        if (StompCommand.CONNECT == accessor.getCommand()){
                            chatRepository.setOnlineUser(userName);
                            chatRepository.enterChatRoom(userName);
                        }
                        else if (StompCommand.DISCONNECT == accessor.getCommand()){
                            chatRepository.deleteOnlineUser(userName);
                            chatRepository.deleteChatRoom(userName);
                        }
                        return message;
                    } else
                        throw new NotLoginException();
                } catch (MalformedJwtException | SignatureException | ExpiredJwtException | NotBearerTokenException | NotLoginException e) {
                    throw new NotLoginException();
                }
            }
        });
    }
}
