package org.ddd.fundamental.repository.core.exception;

public class SubPersistenceException extends PersistenceException{
    public SubPersistenceException(Exception e){
        super(e);
    }

    public SubPersistenceException(){

    }
}
