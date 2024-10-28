package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.factory.EquipmentId;

public interface EquipmentRepository extends BaseRepository<Equipment, EquipmentId> {
}
