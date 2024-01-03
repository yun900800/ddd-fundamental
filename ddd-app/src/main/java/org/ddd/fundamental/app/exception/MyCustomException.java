package org.ddd.fundamental.app.exception;

public class MyCustomException extends RuntimeException{

    public MyCustomException(String msg) {
        super(msg);
    }
}
