package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToolingEquipmentRepository extends BaseHibernateRepository<ToolingEquipment>,
        JpaRepository<ToolingEquipment, EquipmentId> {

    @Modifying
    @Query("delete from ToolingEquipment")
    void deleteAllTooling();

    @Query("from ToolingEquipment")
    @Override
    List<ToolingEquipment> findAll();
}
