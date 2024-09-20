package org.ddd.fundamental.tamagotchi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ddd.fundamental.tamagotchi.domain.Pocket;

import java.util.List;

@AllArgsConstructor
@Getter
public class PocketDto {
    private Pocket.ID id;
    private String name;
    private List<TamagotchiDto> tamagotchis;
}
