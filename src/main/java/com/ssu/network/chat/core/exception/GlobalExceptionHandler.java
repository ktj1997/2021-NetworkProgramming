package com.ssu.network.chat.core.exception;

import com.ssu.network.chat.core.response.error.ErrorResponse;
import com.ssu.network.chat.core.response.error.ErrorSummary;
import com.ssu.network.chat.core.response.error.ExceptionCode;
import com.ssu.network.chat.socket.exception.AlreadyChatException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @MessageExceptionHandler(MessageHandlingException.class)
    public ErrorResponse handleNotLoginError(Exception e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new ErrorSummary(ExceptionCode.AUTHORIZATION_HEADER_MISSING));
    }

    @MessageExceptionHandler(AlreadyChatException.class)
    public ErrorResponse handleAlreadyChatError(Exception e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new ErrorSummary(ExceptionCode.USER_ALREADY_CHAT));
    }
}
