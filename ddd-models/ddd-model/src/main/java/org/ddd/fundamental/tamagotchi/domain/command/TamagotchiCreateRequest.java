package org.ddd.fundamental.tamagotchi.domain.command;

import org.ddd.fundamental.tamagotchi.domain.Status;

public record TamagotchiCreateRequest(String name, Status status){
}
