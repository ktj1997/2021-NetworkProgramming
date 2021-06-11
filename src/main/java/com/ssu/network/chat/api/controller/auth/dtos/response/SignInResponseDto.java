package com.ssu.network.chat.api.controller.auth.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponseDto {
    private String accessToken;
    private String refreshToken;
}
