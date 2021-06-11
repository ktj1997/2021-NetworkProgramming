package com.ssu.network.chat.api.controller.auth.dtos.request;

import com.ssu.network.chat.api.model.user.Gender;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.model.user.UserRole;
import com.ssu.network.chat.api.model.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String gender;

    private int age;

    public User toEntity() {
        return new User(null, userName, password, name, Gender.valueOf(gender), age, UserRole.ROLE_USER, UserStatus.ONLINE, null, new ArrayList<>());
    }
}
