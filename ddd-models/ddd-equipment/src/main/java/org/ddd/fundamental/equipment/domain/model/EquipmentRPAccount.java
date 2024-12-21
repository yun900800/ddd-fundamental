package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountId;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountValue;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;

import javax.persistence.*;

@Entity
@Table( name = "equipment_rpa_account")
public class EquipmentRPAccount extends AbstractAggregateRoot<EquipmentRPAccountId> {

    @Embedded
    private EquipmentRPAccountValue equipmentRPAccountValue;

    @Embedded
    private BusinessRange<WorkOrderComposable> businessRange;

    @SuppressWarnings("unused")
    protected EquipmentRPAccount(){
    }

    private EquipmentRPAccount(Equipment equipment, RPAccount rpAccount){
        super(EquipmentRPAccountId.randomId(EquipmentRPAccountId.class));
        this.equipment = equipment;
        this.rpAccount = rpAccount;
        //this.equipmentAccountId = new EquipmentAccountId(equipment.id(),rpAccount.id());
    }

    private EquipmentRPAccount(Equipment equipment, RPAccount rpAccount,
                               EquipmentRPAccountValue equipmentRPAccountValue){
        this(equipment,rpAccount);
        this.equipmentRPAccountValue = equipmentRPAccountValue;
    }

    public EquipmentRPAccountValue getEquipmentRPAccountValue() {
        if (null ==equipmentRPAccountValue) {
            return null;
        }
        return equipmentRPAccountValue.clone();
    }

    public static EquipmentRPAccount create(Equipment equipment, RPAccount rpAccount,
                                            EquipmentRPAccountValue equipmentRPAccountValue){
        return new EquipmentRPAccount(equipment, rpAccount,equipmentRPAccountValue);
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
