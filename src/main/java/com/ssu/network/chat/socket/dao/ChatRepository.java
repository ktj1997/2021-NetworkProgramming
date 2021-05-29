package com.ssu.network.chat.socket.dao;

import com.ssu.network.chat.socket.handler.RedisSubscriber;
import com.ssu.network.chat.socket.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.channels.Channel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private Set<String> onlineUser;

    @PostConstruct
    private void init() {
        onlineUser = Collections.synchronizedSet(new HashSet<>());
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
}
