package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountId;

import javax.persistence.*;

@Entity
@Table( name = "equipment_rpa_account")
public class EquipmentRPAccount extends AbstractAggregateRoot<EquipmentRPAccountId> {

    //@Embedded
    //private EquipmentAccountId equipmentAccountId;

    @SuppressWarnings("unused")
    protected EquipmentRPAccount(){
    }

    public EquipmentRPAccount(Equipment equipment, RPAccount rpAccount){
        super(EquipmentRPAccountId.randomId(EquipmentRPAccountId.class));
        this.equipment = equipment;
        this.rpAccount = rpAccount;
        //this.equipmentAccountId = new EquipmentAccountId(equipment.id(),rpAccount.id());
    }

    public static EquipmentRPAccount create(Equipment equipment, RPAccount rpAccount){
        return new EquipmentRPAccount(equipment,rpAccount);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("equipmentId")
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("rpAccountId")
    @JoinColumn(name = "rpAccountId")
    private RPAccount rpAccount;


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

    @Override
    public long created() {
        return 0;
    }
}
