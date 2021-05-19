package com.ssu.network.chat.api.service.user.exception;

import com.ssu.network.chat.core.exception.ApiException;

public class UserNotExistException extends ApiException {
    public UserNotExistException() {
        super("Use Not Exist");
    }
}
