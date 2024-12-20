package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountId;

import javax.persistence.*;

@Entity
@Table( name = "equipment_rpa_account")
public class EquipmentRPAccount extends AbstractAggregateRoot<EquipmentRPAccountId> {

    @SuppressWarnings("unused")
    protected EquipmentRPAccount(){
    }

    public EquipmentRPAccount(YearModelValue model, EquipmentType equipmentType,
                     EquipmentMaster master){
        super(EquipmentRPAccountId.randomId(EquipmentRPAccountId.class));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipmentId")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rpAccountId")
    private RPAccount rpAccount;

    @Override
    public long created() {
        return 0;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public RPAccount getRpAccount() {
        return rpAccount;
    }

    public void setRpAccount(RPAccount rpAccount) {
        this.rpAccount = rpAccount;
    }
}
