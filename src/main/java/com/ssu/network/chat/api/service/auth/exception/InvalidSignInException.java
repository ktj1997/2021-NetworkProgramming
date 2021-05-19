package com.ssu.network.chat.api.service.auth.exception;

import com.ssu.network.chat.core.exception.ApiException;

public class InvalidSignInException extends ApiException {
    public InvalidSignInException() {
        super("Invalid Id Or Password");
    }
}
