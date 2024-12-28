package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentResourceRepository extends JpaRepository<EquipmentResource, EquipmentId>,
        CustomEquipmentResourceRepository {
    @Modifying
    @Query("delete from EquipmentResource")
    void deleteAllEquipmentResources();

    @Query("select er  from EquipmentResource er where json_extract(er.equipmentResourceValue.inputs,'$[0]') = :inputId")
    List<EquipmentResource> queryResourcesById(@Param("inputId")String inputId);
}
