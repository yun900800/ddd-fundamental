package org.ddd.fundamental.material.domain.value;
;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class MaterialCharacter implements ValueObject, Cloneable {

    private String name;

    private String spec;

    private int qty;

    private String unit;

    private String address;

    private String transferEquipment;

    @SuppressWarnings("unused")
    private MaterialCharacter(){}

    public MaterialCharacter(String name,String spec,int qty,
                             String unit, String address,String transferEquipment){
        this.name = name;
        this.spec = spec;
        this.qty = qty;
        this.unit = unit;
        this.address = address;
        this.transferEquipment = transferEquipment;
    }

    public String getName() {
        return name;
    }

    public String getSpec() {
        return spec;
    }

    public int getQty() {
        return qty;
    }

    public String getUnit() {
        return unit;
    }

    public String getAddress() {
        return address;
    }

    public String getTransferEquipment() {
        return transferEquipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialCharacter)) return false;
        MaterialCharacter that = (MaterialCharacter) o;
        return qty == that.qty && Objects.equals(name, that.name) && Objects.equals(spec, that.spec) && Objects.equals(unit, that.unit) && Objects.equals(address, that.address) && Objects.equals(transferEquipment, that.transferEquipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, spec, qty, unit, address, transferEquipment);
    }

    @Override
    public String toString() {
        return "MaterialCharacter{" +
                "name='" + name + '\'' +
                ", spec='" + spec + '\'' +
                ", qty=" + qty +
                ", unit='" + unit + '\'' +
                ", address='" + address + '\'' +
                ", transferEquipment='" + transferEquipment + '\'' +
                '}';
    }

    @Override
    public MaterialCharacter clone() {
        try {
            MaterialCharacter clone = (MaterialCharacter) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
