package org.ddd.fundamental.equipment.application.query.specification;

import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class EquipmentSpecification {

    public static Specification<Equipment> assetNoLike(String assetNo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("master").get("assetNo"), "%" + assetNo + "%");
    }

    public static Specification<Equipment> equipmentNameEqual(String equipmentName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("master").get("info").get("name"), equipmentName);
    }

    public static Specification<Equipment> planTimeEqual(LocalDateTime time){
        return (root, query, criteriaBuilder) ->{
            Join<EquipmentPlan,Equipment> planEquipmentJoin = root.join("equipmentPlan");
            return criteriaBuilder.equal(planEquipmentJoin.get("auditable").get("createTime"), time);
        };
    }
}
