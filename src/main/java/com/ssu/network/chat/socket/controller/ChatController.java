package com.ssu.network.chat.socket.controller;

import com.ssu.network.chat.socket.model.ChatMessage;
import com.ssu.network.chat.socket.model.ChatType;
import com.ssu.network.chat.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getMessageType().equals(ChatType.ENTER))
            chatService.enterChatRoom(message.getRoomId());
        chatService.sendMessage(message);
    }
}
