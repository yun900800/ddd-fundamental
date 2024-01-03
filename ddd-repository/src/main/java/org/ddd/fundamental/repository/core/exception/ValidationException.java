package org.ddd.fundamental.repository.core.exception;

/**
 * 验证操作异常
 */
public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {

    }
}
