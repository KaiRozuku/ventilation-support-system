package com.ipze.exception;

public class TransformerNotFoundException extends RuntimeException {

    public TransformerNotFoundException() {
        super("Transformer not found");
    }
}