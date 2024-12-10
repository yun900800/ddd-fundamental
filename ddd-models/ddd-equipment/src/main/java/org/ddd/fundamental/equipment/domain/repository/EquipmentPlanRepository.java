package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentPlanRepository extends JpaRepository<EquipmentPlan, EquipmentId> {
    @Modifying
    @Query("delete from EquipmentPlan")
    void deleteAllEquipmentPlans();
}
