package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;

import java.util.List;

public interface CustomEquipmentRepository {

    List<EquipmentPlan> fetchEquipmentPlan();
}
