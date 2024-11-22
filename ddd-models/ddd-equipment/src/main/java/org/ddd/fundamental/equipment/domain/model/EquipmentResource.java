package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.value.EquipmentResourceId;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;

import javax.persistence.*;

@Entity
@Table(name = "equipment_resource")
public class EquipmentResource extends AbstractEntity<EquipmentResourceId> {

    @Embedded
    private EquipmentResourceValue equipmentResourceValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private EquipmentResource(){}

    private EquipmentResource(EquipmentResourceValue value){
        super(EquipmentResourceId.randomId(EquipmentResourceId.class));
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
