package com.ssu.network.chat.socket.service;

import com.ssu.network.chat.api.controller.room.dtos.EnterResponseDto;
import com.ssu.network.chat.socket.dao.ChatRepository;
import com.ssu.network.chat.socket.handler.RedisPublisher;
import com.ssu.network.chat.socket.handler.RedisSubscriber;
import com.ssu.network.chat.socket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final ChatRepository chatRepository;


    public void sendMessage(ChatMessage message) {
        redisPublisher.publish(getChannelTopic(message.getRoomId()), message);
    }

    public EnterResponseDto enterChatRoom(String roomId) {
        ChannelTopic topic = chatRepository.enterChatRoom(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return new EnterResponseDto(roomId);
    }

    public ChannelTopic getChannelTopic(String roomId) {
        return chatRepository.getTopic(roomId);
    }
}
