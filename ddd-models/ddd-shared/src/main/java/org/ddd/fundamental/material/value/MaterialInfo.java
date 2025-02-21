package org.ddd.fundamental.material.value;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@Slf4j
public class MaterialInfo implements ValueObject,Cloneable {

    private MaterialId materialId;

    private String materialName;

    private int qty;

    private String unit;

    @SuppressWarnings("unused")
    protected MaterialInfo(){
    }

    private MaterialInfo(MaterialId materialId,String materialName,
                         int qty, String unit){
        this.materialId = materialId;
        this.materialName = materialName;
        this.qty = qty;
        this.unit = unit;
    }

    public static MaterialInfo create(MaterialId materialId,String materialName,
                                      int qty, String unit){
        return new MaterialInfo(materialId, materialName, qty, unit);
    }

    public MaterialId getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getQty() {
        return qty;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialInfo)) return false;
        MaterialInfo that = (MaterialInfo) o;
        return qty == that.qty && Objects.equals(materialId, that.materialId)
                && Objects.equals(materialName, that.materialName) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialId, materialName, qty, unit);
    }

    @Override
    public MaterialInfo clone() {
        try {
            MaterialInfo clone = (MaterialInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
