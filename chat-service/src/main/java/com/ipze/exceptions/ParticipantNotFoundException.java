package com.ipze.exceptions;

public class ParticipantNotFoundException extends RuntimeException {

    public ParticipantNotFoundException() {
        super("Participant not found");
    }
}