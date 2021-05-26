package com.ssu.network.chat.socket.service;

import com.ssu.network.chat.api.controller.room.dtos.EnterResponseDto;
import com.ssu.network.chat.socket.dao.ChatRoomRepository;
import com.ssu.network.chat.socket.handler.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init() {
        enterChatRoom("2");
    }

    public EnterResponseDto enterChatRoom(String roomId) {
        chatRoomRepository.enterChatRoom(roomId);
        return new EnterResponseDto(roomId);
    }

    public ChannelTopic getChannelTopic(String roomId){
        return chatRoomRepository.getTopic(roomId);
    }
}
