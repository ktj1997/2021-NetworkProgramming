package com.ssu.network.chat.api.controller.auth.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class SignInRequestDto {
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;
}
