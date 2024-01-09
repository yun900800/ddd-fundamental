package org.ddd.fundamental.repository.Exception;

public class ProductInfoNotExistException extends RuntimeException{

    public ProductInfoNotExistException(String message){
        super(message);
    }
}
