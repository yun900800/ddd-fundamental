package org.ddd.fundamental.tamagotchi.domain;

import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiCreateRequest;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiDeleteException;
import org.ddd.fundamental.tamagotchi.dto.PocketDto;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ddd.fundamental.tamagotchi.domain.Status.CREATED;
import static org.ddd.fundamental.tamagotchi.domain.Status.PENDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PocketTest {
    @Test
    public void shouldCreatePocketWithTamagotchi() {
        Pocket pocket = Pocket.newPocket("My pocket");

        PocketDto dto = pocket.toDto();

        assertEquals(1, dto.getTamagotchis().size());
    }

    @Test
    public void shouldForbidDeletionOfASingleTamagotchi() {
        Pocket pocket = Pocket.newPocket("My pocket");
        PocketDto dto = pocket.toDto();
        UUID tamagotchiId = dto.getTamagotchis().get(0).getId();

        assertThrows(
                TamagotchiDeleteException.class,
                () -> pocket.deleteTamagotchi(tamagotchiId)
        );
    }

    @Test
    public void shouldDeleteTamagotchiById() {
        Pocket pocket = Pocket.newPocket("My pocket");
        UUID tamagotchiId = pocket.createTamagotchi(new TamagotchiCreateRequest("My tamagotchi", CREATED));

        pocket.deleteTamagotchi(tamagotchiId);

        PocketDto dto = pocket.toDto();
        assertThat(dto.getTamagotchis())
                .noneMatch(t -> t.getName().equals("My tamagotchi"));
    }

    @Test
    public void shouldRestoreTamagotchiById() {
        Pocket pocket = Pocket.newPocket("My pocket");
        UUID tamagotchiId = pocket.createTamagotchi(new TamagotchiCreateRequest("My tamagotchi", CREATED));
        pocket.deleteTamagotchi(tamagotchiId);

        pocket.restoreTamagotchi("My tamagotchi");

        PocketDto dto = pocket.toDto();
        assertThat(dto.getTamagotchis())
                .anyMatch(t -> t.getName().equals("My tamagotchi"));
    }

    @Test
    public void shouldRestoreTheLastTamagotchi() {
        Pocket pocket = Pocket.newPocket("My pocket");
        UUID tamagotchiId = pocket.createTamagotchi(new TamagotchiCreateRequest("My tamagotchi", CREATED));
        pocket.deleteTamagotchi(tamagotchiId);
        tamagotchiId = pocket.createTamagotchi(new TamagotchiCreateRequest("My tamagotchi", PENDING));
        pocket.deleteTamagotchi(tamagotchiId);

        pocket.restoreTamagotchi("My tamagotchi");

        PocketDto dto = pocket.toDto();
        assertThat(dto.getTamagotchis())
                .anyMatch(t ->
                        t.getName().equals("My tamagotchi")
                                && t.getStatus().equals(PENDING)
                );
    }
}
