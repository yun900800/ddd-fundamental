package org.ddd.fundamental.repository.core.exception;


/**
 * 工作单元提交事务异常
 */
public class CommitmentException extends RuntimeException{

    public CommitmentException(){}
    public CommitmentException(String message){
        super(message);
    }

}
