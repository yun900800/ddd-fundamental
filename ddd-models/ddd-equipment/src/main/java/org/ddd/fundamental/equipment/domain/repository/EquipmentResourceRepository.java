package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentResourceRepository extends JpaRepository<EquipmentResource, EquipmentId> {
}
