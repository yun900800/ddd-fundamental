package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CraftsmanShipRepository extends BaseRepository<CraftsmanShipTemplate, CraftsmanShipId> {

    @Modifying
    @Query(" delete from CraftsmanShipTemplate ")
    void deleteCraftsmanShips();

}
