package com.ssu.network.chat.socket.controller;

import com.ssu.network.chat.socket.handler.RedisPublisher;
import com.ssu.network.chat.socket.model.ChatMessage;
import com.ssu.network.chat.socket.model.ChatType;
import com.ssu.network.chat.socket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations messageTemplate;
    private final RedisPublisher redisPublisher;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getMessageType().equals(ChatType.ENTER)) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            chatRoomService.enterChatRoom(message.getRoomId());
            messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        }
        redisPublisher.publish(chatRoomService.getChannelTopic(message.getRoomId()), message);
    }
}
