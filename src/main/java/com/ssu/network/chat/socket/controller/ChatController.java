package com.ssu.network.chat.socket.controller;

import com.ssu.network.chat.core.security.JwtProvider;
import com.ssu.network.chat.socket.exception.NotLoginException;
import com.ssu.network.chat.socket.model.ChatMessage;
import com.ssu.network.chat.socket.model.ChatType;
import com.ssu.network.chat.socket.service.ChatService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("Authorization") String token) {
        if (token != null) {
            token = jwtProvider.checkingAndParsingBearer(token);
            Claims claims = jwtProvider.getClaimsFromToken(token);
            String userName = jwtProvider.getUserNameFromClaims(claims);
            if (message.getMessageType() == ChatType.ENTER)
                chatService.setOnlineUser(userName);
            else if (message.getMessageType() == ChatType.CONNECT) {
                chatService.enterChatRoom(userName, message.getRoomId());
                message.setMessage("입장");
            } else if (message.getMessageType() == ChatType.DISCONNECT) {
                chatService.exitChatRoom(userName, message.getRoomId());
                message.setMessage("퇴장");
            } else if (message.getMessageType() == ChatType.EXIT)
                chatService.setOfflineUser(userName);
            message.setSender(userName);
            chatService.sendMessage(message);
        } else
            throw new NotLoginException();
    }
}
