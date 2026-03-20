package com.ipze.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(){
        super("Email already exist");
    }
}