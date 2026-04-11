package com.ipze.exception;


public class InvalidPasswordOrEmailException extends RuntimeException {

    public InvalidPasswordOrEmailException() {
        super("Invalid password or email");
    }
}