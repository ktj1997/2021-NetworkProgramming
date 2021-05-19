package com.ssu.network.chat.api.controller.auth;


import com.ssu.network.chat.api.controller.auth.dtos.request.SignInRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.request.SignUpRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.response.SignInResponseDto;
import com.ssu.network.chat.api.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public boolean signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return authService.signUp(dto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody @Valid SignInRequestDto dto) {
        return authService.signIn(dto);
    }
}