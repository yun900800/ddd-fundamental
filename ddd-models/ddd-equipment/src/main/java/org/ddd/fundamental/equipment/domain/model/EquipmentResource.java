package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;
import org.ddd.fundamental.factory.EquipmentId;


import javax.persistence.*;

@Entity
@Table(name = "equipment_resource")
public class EquipmentResource extends AbstractEntity<EquipmentId> {

    @Embedded
    private EquipmentResourceValue equipmentResourceValue;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "equipment_id")
    @MapsId
    @JoinColumn(name = "id")
    private Equipment equipment;

    @SuppressWarnings("unused")
    protected EquipmentResource(){}

//    protected EquipmentResource(EquipmentId id){
//        super(id);
//    }

    private EquipmentResource(EquipmentResourceValue value){
        super(EquipmentId.randomId(EquipmentId.class));
        this.equipmentResourceValue = value;
    }

    public static EquipmentResource create(EquipmentResourceValue value){
        return new EquipmentResource(value);
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentResourceValue getEquipmentResourceValue() {
        return equipmentResourceValue;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public long created() {
        return 0;
    }
}
