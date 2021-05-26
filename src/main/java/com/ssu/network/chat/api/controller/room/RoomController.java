package com.ssu.network.chat.api.controller.room;

import com.ssu.network.chat.api.controller.room.dtos.EnterResponseDto;
import com.ssu.network.chat.socket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/user")
    public EnterResponseDto enterChatting(@RequestParam String username) {
        return chatRoomService.enterChatRoom(username);
    }
}
