package com.ssu.network.chat.api.controller.user.dtos;

import com.ssu.network.chat.api.model.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private String nickName;
    private Gender gender;
    private int age;
    private InterestDto interests;
}
