package com.ssu.network.chat.socket.dao;

import com.ssu.network.chat.socket.model.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChatRoomRepository {
    private final Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public ChatRoom findChatRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createChatRoom() {
        ChatRoom chatRoom = new ChatRoom();
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;

    }

}
