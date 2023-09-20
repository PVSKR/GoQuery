package com.stackroute.exception;

@SuppressWarnings("serial")
public class AuthNotFoundException extends Exception {
    private String message;

    public AuthNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public AuthNotFoundException() {
    }

}