package org.ddd.fundamental.tamagotchi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ddd.fundamental.tamagotchi.domain.Status;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class TamagotchiDto {

    private UUID id;

    private String name;

    Status status;

}
