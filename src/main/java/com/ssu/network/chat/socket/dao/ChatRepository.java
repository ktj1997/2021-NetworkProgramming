package com.ssu.network.chat.socket.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private Set<String> onlineUser;
    private Map<String, Integer> chatParticipant;

    @PostConstruct
    private void init() {
        onlineUser = Collections.synchronizedSet(new HashSet<>());
        chatParticipant = new ConcurrentHashMap<>();
    }

    public void participate(String roomId) {
        if (!chatParticipant.containsKey(roomId))
            chatParticipant.put(roomId, 1);
        else
            chatParticipant.put(roomId, 2);
    }

    public void exit(String roomId) {
        chatParticipant.put(roomId, 1);
    }

    public boolean canParticipate(String roomId) {
        return !chatParticipant.containsKey(roomId) ||  chatParticipant.get(roomId) < 2;
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
