package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class MachineShopValueObject implements ValueObject, Cloneable {


    @AttributeOverrides({
            @AttributeOverride(name = "name" , column =@Column( name = "machine_shop_name", nullable = false)),
            @AttributeOverride(name = "desc" , column =@Column( name = "machine_shop_desc", nullable = false)),
            @AttributeOverride(name = "isUse" , column =@Column( name = "machine_shop_is_use", nullable = false))
    })
    private ChangeableInfo machineShop;

    @SuppressWarnings("unused")
    private MachineShopValueObject(){}

    public MachineShopValueObject(ChangeableInfo info){
        this.machineShop = info;
    }

    public MachineShopValueObject changeName(String name) {
        this.machineShop = this.machineShop.changeName(name);
        return this;
    }

    public MachineShopValueObject changeDesc(String desc) {
        this.machineShop = this.machineShop.changeDesc(desc);
        return this;
    }

    public String name(){
        return this.machineShop.getName();
    }

    public String desc(){
        return this.machineShop.getDesc();
    }

    public ChangeableInfo getMachineShop() {
        return machineShop.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineShopValueObject)) return false;
        MachineShopValueObject that = (MachineShopValueObject) o;
        return Objects.equals(machineShop, that.machineShop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineShop);
    }

    @Override
    public String toString() {
        return "MachineShopValueObject{" +
                "machineShop=" + machineShop +
                '}';
    }


    @Override
    public MachineShopValueObject clone() {
        try {
            MachineShopValueObject clone = (MachineShopValueObject) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
