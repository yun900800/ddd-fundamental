package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment extends AbstractAggregateRoot<EquipmentId> {

    @AttributeOverrides({
            @AttributeOverride(name  = "name", column = @Column(name = "equipment_name", nullable = false)),
            @AttributeOverride(name  = "desc", column = @Column(name = "equipment_desc", nullable = false))
    })
    @Embedded
    private ChangeableInfo info;

    private EquipmentType equipmentType;

    @Embedded
    private YearModelValueObject model;

    /**
     * 设备需要人员数量
     */
    private int workerCount = 0;

    /**
     * 人员具备的资质
     */
    private String qualification;

    @Embedded
    private EquipmentMaster master;

    @SuppressWarnings("unused")
    private Equipment(){
    }

    public Equipment(ChangeableInfo changeableInfo, EquipmentType equipmentType,
                     EquipmentMaster master){
        super(DomainObjectId.randomId(EquipmentId.class));
        this.info = changeableInfo;
        this.equipmentType = equipmentType;
        this.master = master;
    }
}
