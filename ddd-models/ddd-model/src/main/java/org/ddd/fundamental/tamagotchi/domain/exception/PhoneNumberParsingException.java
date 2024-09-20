package org.ddd.fundamental.tamagotchi.domain.exception;

public class PhoneNumberParsingException extends RuntimeException{
    public PhoneNumberParsingException(String msg){
       super(msg);
    }
    public PhoneNumberParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberParsingException(Throwable cause) {
        super(cause);
    }

    public PhoneNumberParsingException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
