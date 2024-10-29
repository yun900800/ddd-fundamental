package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment extends AbstractAggregateRoot<EquipmentId> {

    private EquipmentType equipmentType;

    @Embedded
    private YearModelValueObject model;


    @Embedded
    private EquipmentMaster master;

    @SuppressWarnings("unused")
    private Equipment(){
    }

    public Equipment(ChangeableInfo changeableInfo, EquipmentType equipmentType,
                     EquipmentMaster master){
        super(DomainObjectId.randomId(EquipmentId.class));
        this.equipmentType = equipmentType;
        this.master = master;
    }
}
