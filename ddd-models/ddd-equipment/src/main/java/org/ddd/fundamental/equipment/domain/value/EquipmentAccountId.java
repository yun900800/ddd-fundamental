package org.ddd.fundamental.equipment.domain.value;

import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class EquipmentAccountId extends DomainObjectId implements ValueObject {

    @Column(name = "equipment_id")
    private EquipmentId equipmentId;

    @Column(name = "rp_account_id")
    private RPAccountId rpAccountId;

    @Override
    public String toString() {
        return "EquipmentAccountId{" +
                "equipmentId=" + equipmentId +
                ", rpAccountId=" + rpAccountId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentAccountId)) return false;
        EquipmentAccountId that = (EquipmentAccountId) o;
        return Objects.equals(equipmentId, that.equipmentId) && Objects.equals(rpAccountId, that.rpAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId, rpAccountId);
    }

    private EquipmentAccountId() {
        super("");
    }

    public EquipmentAccountId(String uuid){
        super(uuid);
    }

    public EquipmentAccountId(EquipmentId equipmentId, RPAccountId rpAccountId) {
        super("");
        this.equipmentId = equipmentId;
        this.rpAccountId = rpAccountId;
    }

    public EquipmentId getEquipmentId() {
        return equipmentId;
    }

    public RPAccountId getRpAccountId() {
        return rpAccountId;
    }
}
