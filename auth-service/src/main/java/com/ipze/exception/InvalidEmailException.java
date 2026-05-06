package com.ipze.exception;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String msg){
        super(msg);
    }
}
