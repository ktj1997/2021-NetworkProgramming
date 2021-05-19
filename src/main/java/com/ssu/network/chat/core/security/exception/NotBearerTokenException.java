package com.ssu.network.chat.core.security.exception;

import com.ssu.network.chat.core.exception.ApiException;

public class NotBearerTokenException extends ApiException {

    public NotBearerTokenException() {
        super("Token Must Started With Bearer ");
    }
}
