package com.ssu.network.chat.api.controller.chat;

import com.ssu.network.chat.api.controller.chat.dtos.EnterResponseDto;
import com.ssu.network.chat.socket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/user")
    public EnterResponseDto enterChatting(@RequestParam String username) {
        return chatRoomService.enterChatRoom(username);
    }
}
