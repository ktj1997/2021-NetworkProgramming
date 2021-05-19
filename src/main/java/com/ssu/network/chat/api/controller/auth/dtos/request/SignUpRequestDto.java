package com.ssu.network.chat.api.controller.auth.dtos.request;

import com.ssu.network.chat.api.model.user.Gender;
import com.ssu.network.chat.api.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

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
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birth;

    public User toEntity() {
        return new User(null, userName, password, name, gender, birth, null);
    }
}
