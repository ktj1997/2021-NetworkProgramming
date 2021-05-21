package com.ssu.network.chat.socket.service;

import com.ssu.network.chat.socket.dao.ChannelTopicRepository;
import com.ssu.network.chat.socket.dao.ChatRoomRepository;
import com.ssu.network.chat.socket.exception.TopicNotExistException;
import com.ssu.network.chat.socket.handler.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChannelTopicRepository channelTopicRepository;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;


    public ChannelTopic getChannelTopic(String roomId) {
        ChannelTopic channelTopic = channelTopicRepository.findTopicByRoomId(roomId);
        if (channelTopic == null)
            throw new TopicNotExistException();
        return channelTopic;
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic channelTopic = channelTopicRepository.findTopicByRoomId(roomId);
        if (channelTopic == null) {
            channelTopic = channelTopicRepository.save(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, channelTopic);
        }


    }
}
