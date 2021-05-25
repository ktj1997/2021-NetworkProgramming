package com.ssu.network.chat.socket.handler;


import com.ssu.network.chat.socket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage chatMessage) {
        redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
    }
}
