package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends BaseRepository<Equipment, EquipmentId> {

    @Query("select e from Equipment e where e.master.info.name like %?1%")
    List<Equipment> queryByName(String name);

    @Modifying
    @Query("delete from Equipment")
    void deleteAllEquipments();
}
