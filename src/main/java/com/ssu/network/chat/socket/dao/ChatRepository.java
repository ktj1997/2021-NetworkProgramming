package com.ssu.network.chat.socket.dao;

import com.ssu.network.chat.socket.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final String CHATROOM = "CHATROOM";


    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Set<String> onlineUser;
    private ConcurrentHashMap<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        onlineUser = Collections.synchronizedSet(new HashSet<>());
        topics = new ConcurrentHashMap<>();
    }

    public void deleteOnlineUser(String userName) {
        onlineUser.remove(userName);
    }

    public void setOnlineUser(String userName) {
        onlineUser.add(userName);
    }

    public Set<String> getOnlineUserList() {
        return onlineUser;

    }

    public ChannelTopic enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null)
            topic = new ChannelTopic(roomId);
        topics.put(roomId, topic);
        return topic;
    }

    public ChatRoom findChatRoomById(String roomId) {
        return opsHashChatRoom.get(CHATROOM, roomId);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom(name);
        opsHashChatRoom.put(CHATROOM, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
