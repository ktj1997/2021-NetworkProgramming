package com.ssu.network.chat.socket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ChatMessage {
    private ChatType messageType;
    private String roomId;

    @Setter
    private String sender;
    @Setter
    private String message;
}
