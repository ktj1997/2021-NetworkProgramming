package com.ssu.network.chat.api.service.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;

public interface UserService {
    public boolean chooseInterest(InterestDto dto);
}
