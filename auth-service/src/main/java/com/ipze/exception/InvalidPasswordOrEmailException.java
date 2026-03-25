package com.ipze.exception;

import java.util.Arrays;

public class InvalidPasswordOrEmailException extends RuntimeException {

    public InvalidPasswordOrEmailException() {
        super("Invalid password or email");
    }
    public InvalidPasswordOrEmailException(String ... strings) {
        super(Arrays.toString(strings));
    }
}