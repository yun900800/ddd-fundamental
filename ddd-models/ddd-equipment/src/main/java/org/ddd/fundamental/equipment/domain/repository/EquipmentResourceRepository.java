package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentResourceRepository extends JpaRepository<EquipmentResource, EquipmentId>,
        CustomEquipmentResourceRepository {
    @Modifying
    @Query("delete from EquipmentResource")
    void deleteAllEquipmentResources();
}
