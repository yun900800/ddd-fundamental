package org.ddd.fundamental.tamagotchi.service;

import lombok.RequiredArgsConstructor;
import org.ddd.fundamental.tamagotchi.domain.Pocket;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiCreateRequest;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiUpdateRequest;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiNameInvalidException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PocketService {

    private final EntityManager em;

    @Transactional
    public Pocket.ID createPocket(String name) {
        Pocket pocket = Pocket.newPocket(name);
        em.persist(pocket);
        return pocket.getId();
    }

    @Transactional
    public UUID createTamagotchi(Pocket.ID pocketId, TamagotchiCreateRequest request) {
        Pocket pocket = em.find(Pocket.class, pocketId);
        return pocket.createTamagotchi(request);
    }

    @Transactional
    public void updateTamagotchi(UUID tamagotchiId, TamagotchiUpdateRequest request) {
        validateNameUnique(tamagotchiId,request);

        Pocket.ID pocketId = em.createQuery(
                        "SELECT t.pocket.id AS id FROM Tamagotchi t WHERE t.id = :tamagotchiId",
                        Pocket.ID.class
                )
                .setParameter("tamagotchiId", tamagotchiId)
                .getSingleResult();

        Pocket pocket = em.find(Pocket.class, pocketId);
        pocket.updateTamagotchi(tamagotchiId, request);
    }

    private void validateNameUnique(UUID tamagotchiId, TamagotchiUpdateRequest request){
        boolean nameIsNotUnique = em.createQuery(
                        """
                            SELECT COUNT(t) > 0 FROM Tamagotchi t
                            WHERE t.id <> :tamagotchiId AND t.name = :newName
                            """,
                        Boolean.class
                ).setParameter("tamagotchiId", tamagotchiId)
                .setParameter("newName", request.name())
                .getSingleResult();

        if (nameIsNotUnique) {
            throw new TamagotchiNameInvalidException("Tamagotchi name is not unique: " + request.name());
        }
    }

    @Transactional
    public void updateTamagotchiPerformance(UUID tamagotchiId, TamagotchiUpdateRequest request) {
        validateNameUnique(tamagotchiId,request);
        Pocket pocket = em.createQuery(
                        """
                            SELECT p FROM Pocket p
                            LEFT JOIN FETCH p.tamagotchis t
                            WHERE t.id = :tamagotchiId
                            """,
                        Pocket.class
                ).setParameter("tamagotchiId", tamagotchiId)
                .getSingleResult();
        pocket.updateTamagotchi(tamagotchiId, request);
    }
}
