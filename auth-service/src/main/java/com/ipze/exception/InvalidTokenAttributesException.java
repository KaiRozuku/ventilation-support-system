package com.ipze.exception;

public class InvalidTokenAttributesException extends IllegalArgumentException{

    public InvalidTokenAttributesException(){
        super("Token missing attribute/s");
    }

}
