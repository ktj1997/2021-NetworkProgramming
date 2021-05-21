package com.ssu.network.chat.socket.dao;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChannelTopicRepository {
    private final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();


    public ChannelTopic findTopicByRoomId(String roomId) {
        return topics.getOrDefault(roomId, null);
    }

    public ChannelTopic save(String roomId) {
        ChannelTopic channelTopic = new ChannelTopic(roomId);
        topics.put(roomId, channelTopic);
        return channelTopic;
    }
}
