package com.ssu.network.chat.socket.service;

import com.ssu.network.chat.api.controller.room.dtos.EnterResponseDto;
import com.ssu.network.chat.api.dao.user.UserRepository;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.model.user.UserStatus;
import com.ssu.network.chat.api.service.user.exception.UserNotExistException;
import com.ssu.network.chat.socket.dao.ChatRepository;
import com.ssu.network.chat.socket.exception.AlreadyChatException;
import com.ssu.network.chat.socket.handler.RedisPublisher;
import com.ssu.network.chat.socket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChannelTopic channelTopic;


    public void sendMessage(ChatMessage message) {
        redisPublisher.publish(channelTopic, message);
    }


    public EnterResponseDto enterChatRoom(String userName, String roomId) {
        User roomOwner = userRepository.findByUserName(roomId).orElseThrow(UserNotExistException::new);
        User participant = userRepository.findByUserName(userName).orElseThrow(UserNotExistException::new);

        if (roomOwner.getStatus() == UserStatus.ONLINE) {
            roomOwner.setStatus(UserStatus.CHAT);
            participant.setStatus(UserStatus.CHAT);
            return new EnterResponseDto(roomId);
        } else
            throw new AlreadyChatException();
    }

    public void exitChatRoom(String userName, String roomId) {
        User roomOwner = userRepository.findByUserName(roomId).orElseThrow(UserNotExistException::new);
        User participant = userRepository.findByUserName(userName).orElseThrow(UserNotExistException::new);

        roomOwner.setStatus(UserStatus.ONLINE);
        participant.setStatus(UserStatus.ONLINE);
    }

    public void setOnlineUser(String userName) {
        chatRepository.setOnlineUser(userName);
    }
    public void setOfflineUser(String userName) {
        chatRepository.deleteOnlineUser(userName);
    }
}
