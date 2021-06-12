package com.ssu.network.chat.api.service.auth;

import com.ssu.network.chat.api.controller.auth.dtos.request.SignInRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.request.SignUpRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.response.SignInResponseDto;

public interface AuthService {
    boolean signUp(SignUpRequestDto dto);

    SignInResponseDto signIn(SignInRequestDto dto);

    boolean checkIdDuplication(String id);

    boolean checkNickNameDuplication(String nickname);
}
