package com.ipze.exceptions;

public class ChatNotFoundException extends RuntimeException{

    public ChatNotFoundException(){
        super("Chat not found");
    }
}