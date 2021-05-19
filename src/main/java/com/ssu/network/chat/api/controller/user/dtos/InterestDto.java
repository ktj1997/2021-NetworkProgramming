package com.ssu.network.chat.api.controller.user.dtos;

import com.ssu.network.chat.api.model.user.Interest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class InterestDto {

    @NotEmpty
    List<Interest> interests;
}
