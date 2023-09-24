package org.ddd.fundamental.repository.core.exception;

import java.beans.Expression;

public class PersistenceException extends RuntimeException{

    public PersistenceException(Exception e){
        super(e);
    }

    public PersistenceException(){

    }

}
