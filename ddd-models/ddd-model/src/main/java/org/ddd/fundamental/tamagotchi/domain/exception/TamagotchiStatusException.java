package org.ddd.fundamental.tamagotchi.domain.exception;

public class TamagotchiStatusException extends RuntimeException{
    public TamagotchiStatusException(String message) {
        super(message);
    }
}
