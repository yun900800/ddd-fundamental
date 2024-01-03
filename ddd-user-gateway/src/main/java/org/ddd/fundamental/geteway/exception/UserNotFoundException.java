package org.ddd.fundamental.geteway.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){

    }

    public UserNotFoundException(String message){
        super(message);
    }
}
