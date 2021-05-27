package com.ssu.network.chat.api.controller.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;
import com.ssu.network.chat.api.controller.user.dtos.UserDto;
import com.ssu.network.chat.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/interest")
    public boolean changeInterest(@RequestBody @Valid InterestDto dto) {
        return userService.chooseInterest(dto);
    }


    @GetMapping("/participant")
    public List<UserDto> getOnlineUser(){
        return userService.getOnlineUser();
    }
}
