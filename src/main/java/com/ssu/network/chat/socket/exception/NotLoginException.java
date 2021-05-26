package com.ssu.network.chat.socket.exception;

import com.ssu.network.chat.core.exception.ApiException;

public class NotLoginException extends ApiException {
    public NotLoginException(){
        super("User Not Login");
    }
}
