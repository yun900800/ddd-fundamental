package org.ddd.fundamental.repository.core.exception;


/**
 * 持久层持久化异常
 */
public class PersistenceException extends RuntimeException{

    public PersistenceException(Exception e){
        super(e);
    }

    public PersistenceException(){

    }

}
