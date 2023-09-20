package com.stackroute.goquery.userservice.Exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class UserAlreadyExistException extends Exception{

    private String message;

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String message){
        super();
        this.message = message;
    }

    public UserAlreadyExistException(String s, HttpStatus conflict) {
    }
}
