package org.ddd.fundamental.equipment.domain.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.ddd.fundamental.equipment.domain.repository.CustomEquipmentRepository;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ComponentScan
@Slf4j
public class EquipmentRepositoryImpl implements CustomEquipmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EquipmentPlan> fetchEquipmentPlan() {
        TypedQuery<EquipmentPlan> query = entityManager.createQuery(
             "select ep from Equipment e, EquipmentPlan ep where e.equipmentPlan = ep"
        ,EquipmentPlan.class);
        return query.getResultList();
    }
}
