package com.ssu.network.chat.socket.exception;

public class TopicNotExistException extends RuntimeException {
    public TopicNotExistException() {
        super("Topic is Not Exists");

    }
}
