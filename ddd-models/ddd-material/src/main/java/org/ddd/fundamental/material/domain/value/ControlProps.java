package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.shared.api.material.enums.MaterialType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 控制属性
 */
@Embeddable
@MappedSuperclass
public class ControlProps implements ValueObject, Cloneable {

    private String materialLevel;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    @SuppressWarnings("unused")
    private ControlProps(){}

    private ControlProps(String materialLevel,
                         MaterialType materialType){
        this.materialLevel = materialLevel;
        this.materialType = materialType;
    }

    public static ControlProps create(String materialLevel,
                                      MaterialType materialType){
        return new ControlProps(materialLevel,materialType);
    }

    public String getMaterialLevel() {
        return materialLevel;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControlProps)) return false;
        ControlProps that = (ControlProps) o;
        return Objects.equals(materialLevel, that.materialLevel) && materialType == that.materialType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialLevel, materialType);
    }

    @Override
    public String toString() {
        return "ControlProps{" +
                "materialLevel='" + materialLevel + '\'' +
                ", materialType=" + materialType +
                '}';
    }

    @Override
    public ControlProps clone() {
        try {
            ControlProps clone = (ControlProps) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
