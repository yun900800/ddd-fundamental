package org.ddd.fundamental.tamagotchi.service;

import org.ddd.fundamental.tamagotchi.domain.Pocket;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiCreateRequest;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiUpdateRequest;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiNameInvalidException;
import org.ddd.fundamental.tamagotchi.dto.PocketDto;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;



import javax.persistence.EntityManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ddd.fundamental.tamagotchi.domain.Status.CREATED;
import static org.ddd.fundamental.tamagotchi.domain.Status.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PocketServiceTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EntityManager em;
    @Autowired
    private PocketService pocketService;



    @Test
    public void shouldCreateNewPocket() {
        Pocket.ID pocketId = pocketService.createPocket("New pocket");
        PocketDto pocketDto = transactionTemplate.execute(
                s -> em.find(Pocket.class, pocketId).toDto()
        );
        assertEquals("New pocket",pocketDto.getName());
        assertEquals("Default",pocketDto.getTamagotchis().get(0).getName());
        assertEquals("8613570863933",pocketDto.getTamagotchis().get(0).getPhoneNumber().getValue());
    }

    @Test
    void shouldCreateTamagotchi() {
        Pocket.ID pocketId = pocketService.createPocket("New pocket");

        UUID tamagotchiId = pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("my tamagotchi", CREATED)
        );

        PocketDto dto = transactionTemplate.execute(
                s -> em.find(Pocket.class, pocketId).toDto()
        );
        assertThat(dto.getTamagotchis())
                .anyMatch(t ->
                        t.getName().equals("my tamagotchi")
                                && t.getStatus().equals(CREATED)
                                && t.getId().equals(tamagotchiId)
                );
    }

    @Test
    public void shouldUpdateTamagotchi() {
        Pocket.ID pocketId = pocketService.createPocket("New pocket");
        UUID tamagotchiId = pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("my tamagotchi", CREATED)
        );
        System.out.println("SQL----");
        pocketService.updateTamagotchi(
                tamagotchiId,
                new TamagotchiUpdateRequest("another tamagotchi", PENDING)
        );
        System.out.println("SQL----");

        PocketDto dto = transactionTemplate.execute(
                s -> em.find(Pocket.class, pocketId).toDto()
        );
        assertThat(dto.getTamagotchis())
                .anyMatch(t ->
                        t.getName().equals("another tamagotchi")
                                && t.getStatus().equals(PENDING)
                                && t.getId().equals(tamagotchiId)
                );
    }


    @Test
    void shouldUpdateTamagotchiIfThereAreMultipleOnes() {
        Pocket.ID pocketId = pocketService.createPocket("New pocket1");
        UUID tamagotchiId = pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("Cat", CREATED)
        );
        pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("Dog", CREATED)
        );

        System.out.println("SQL----");
        assertThrows(
                TamagotchiNameInvalidException.class,
                () -> pocketService.updateTamagotchi(tamagotchiId, new TamagotchiUpdateRequest("Dog", CREATED))
        );
    }

    @Test
    void shouldUpdateTamagotchiPerformanceIfThereAreMultipleOnes() {
        Pocket.ID pocketId = pocketService.createPocket("New pocket1");
        UUID tamagotchiId = pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("Cat", CREATED)
        );
        pocketService.createTamagotchi(
                pocketId,
                new TamagotchiCreateRequest("Dog", CREATED)
        );

        System.out.println("SQL----");
        assertThrows(
                TamagotchiNameInvalidException.class,
                () -> pocketService.updateTamagotchiPerformance(tamagotchiId, new TamagotchiUpdateRequest("Dog", CREATED))
        );
    }

}
