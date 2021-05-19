package com.ssu.network.chat.core.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    int status;
    ErrorSummary error;
}
