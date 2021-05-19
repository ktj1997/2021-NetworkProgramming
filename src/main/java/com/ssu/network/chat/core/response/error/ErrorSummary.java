package com.ssu.network.chat.core.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorSummary {
    String message;
    String code;
    LocalDateTime timeStamp = LocalDateTime.now();

    public ErrorSummary(ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.code = exceptionCode.getCode();
    }


}
