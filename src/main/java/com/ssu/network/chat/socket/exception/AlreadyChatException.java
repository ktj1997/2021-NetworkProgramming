package com.ssu.network.chat.socket.exception;

public class AlreadyChatException extends RuntimeException{
    public AlreadyChatException(){
        super("User Already Chatting");
    }
}
