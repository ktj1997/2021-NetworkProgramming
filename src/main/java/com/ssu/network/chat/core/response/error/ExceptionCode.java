package com.ssu.network.chat.core.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    INVALID_TOKEN("AUTH-001", "Token is Invalid"),

    MALFORMED_TOKEN("AUTH-002", "Token is Malformed"),

    EXPIRE_TOKEN("AUTH-003", "Token is Expired"),

    NOT_BEARER_FORMAT("AUTH-004", "Token Must Stared With Bearer ");

    private final String code;
    private final String message;
}
