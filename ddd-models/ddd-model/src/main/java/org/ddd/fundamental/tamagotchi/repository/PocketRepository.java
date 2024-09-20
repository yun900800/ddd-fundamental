package org.ddd.fundamental.tamagotchi.repository;

import org.ddd.fundamental.tamagotchi.domain.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PocketRepository extends JpaRepository<Pocket, Pocket.ID> {

    @Query("SELECT p FROM Pocket p LEFT JOIN p.tamagotchis t WHERE t.id = :tamagotchiId")
    Optional<Pocket> findByTamagotchiId(UUID tamagotchiId);
}
