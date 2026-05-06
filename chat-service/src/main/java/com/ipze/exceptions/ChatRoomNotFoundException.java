package com.ipze.exceptions;

public class ChatRoomNotFoundException extends RuntimeException{

    public ChatRoomNotFoundException(){
        super("Chat room not found");
    }
}