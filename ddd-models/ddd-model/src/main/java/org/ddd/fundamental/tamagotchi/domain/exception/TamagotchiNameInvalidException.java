package org.ddd.fundamental.tamagotchi.domain.exception;

public class TamagotchiNameInvalidException extends RuntimeException{

    public TamagotchiNameInvalidException(String msg){
        super(msg);
    }
}
