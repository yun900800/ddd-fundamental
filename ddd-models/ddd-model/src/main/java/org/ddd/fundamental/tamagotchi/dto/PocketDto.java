package org.ddd.fundamental.tamagotchi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class PocketDto {
    private UUID id;
    private String name;
    private List<TamagotchiDto> tamagotchis;
}
