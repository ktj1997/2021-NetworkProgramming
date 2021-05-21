package com.ssu.network.chat.socket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ChatMessage {
    private ChatType messageType;
    private String sender;
    private String roomId;
    @Setter
    private String message;
}
