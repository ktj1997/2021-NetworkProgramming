package com.ssu.network.chat.api.controller.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;
import com.ssu.network.chat.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/interest")
    public boolean changeInterest(@RequestBody @Valid InterestDto dto) {
        return userService.chooseInterest(dto);
    }
}
