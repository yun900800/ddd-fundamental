package org.ddd.fundamental.equipment.application.query.specification;

import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

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
            return criteriaBuilder.between(planEquipmentJoin.get("auditable").get("createTime"), time.plusDays(-10),time);
        };
    }
}
