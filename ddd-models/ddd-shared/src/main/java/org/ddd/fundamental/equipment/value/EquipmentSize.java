package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class EquipmentSize implements ValueObject,Cloneable {

    public final double width;

    public final double length;

    public final double height;

    private EquipmentSize(){
        this.width = 0.0;
        this.length = 0.0;
        this.height = 0.0;
    }

    private EquipmentSize(double width,
                         double length,
                         double height){
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public static EquipmentSize create(double width,
                                       double length,
                                       double height) {
        return new EquipmentSize(width,length,height);
    }

    @Override
    public EquipmentSize clone() {
        try {
            EquipmentSize clone = (EquipmentSize) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "EquipmentSize{" +
                "width=" + width +
                ", length=" + length +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentSize)) return false;
        EquipmentSize that = (EquipmentSize) o;
        return Double.compare(that.width, width) == 0 && Double.compare(that.length, length) == 0 && Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length, height);
    }
}
