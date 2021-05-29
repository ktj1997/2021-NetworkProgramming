package com.ssu.network.chat.api.controller.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestDto {
    @NotEmpty
    List<String> interests;
}
