package org.ddd.fundamental.workprocess.exception;

public class ProductServiceNotAvailableException extends RuntimeException{
    public ProductServiceNotAvailableException(String msg){
        super(msg);
    }
}
