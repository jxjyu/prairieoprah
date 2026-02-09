package com.example.exceptions;

public class InvalidPasscodeException extends RuntimeException {
    public InvalidPasscodeException(String message) {
        super(message);
    }
}
