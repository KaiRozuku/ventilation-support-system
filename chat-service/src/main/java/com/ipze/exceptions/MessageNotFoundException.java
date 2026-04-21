package com.ipze.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(){
        super("Message not found");
    }
}