package com.ssu.network.chat.api.service.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;
import com.ssu.network.chat.api.controller.user.dtos.UserDto;

import java.util.List;

public interface UserService {
    boolean chooseInterest(InterestDto dto);
    List<UserDto> getOnlineUser();
}
