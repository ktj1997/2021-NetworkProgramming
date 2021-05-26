package com.ssu.network.chat.socket.controller;

import com.ssu.network.chat.core.security.JwtProvider;
import com.ssu.network.chat.core.security.exception.NotBearerTokenException;
import com.ssu.network.chat.socket.exception.NotLoginException;
import com.ssu.network.chat.socket.handler.RedisPublisher;
import com.ssu.network.chat.socket.model.ChatMessage;
import com.ssu.network.chat.socket.model.ChatType;
import com.ssu.network.chat.socket.service.ChatRoomService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;
    private final RedisPublisher redisPublisher;
    private final JwtProvider jwtProvider;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("Authorization") String jwtToken) {
        String userName = null;
        try {
            String token = jwtProvider.checkingAndParsingBearer(jwtToken);
            Claims claims = jwtProvider.getClaimsFromToken(token);
            if (claims != null)
                userName = jwtProvider.getUserNameFromClaims(claims);

            if (message.getMessageType().equals(ChatType.ENTER)) {
                chatRoomService.enterChatRoom(message.getRoomId());
                message.setMessage(userName + "님이 입장하셨습니다.");
                message.setSender("[System]");
            } else
                message.setSender(userName);
            redisPublisher.publish(chatRoomService.getChannelTopic(message.getRoomId()), message);

        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | NotBearerTokenException e) {
            e.printStackTrace();
        }
    }
}
