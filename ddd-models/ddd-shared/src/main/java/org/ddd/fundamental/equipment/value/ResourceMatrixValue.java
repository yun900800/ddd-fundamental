package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
@Embeddable
public class ResourceMatrixValue implements ValueObject, Cloneable {

    /**
     * 资源对应的id
     */
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "resource_ids")
    private Set<EquipmentId> resourceIds;

    /**
     * 资源组合对应的设置时间.换线时间和检查时间
     */
    private AuxiliaryWorkTime auxiliaryWorkTime;

    @SuppressWarnings("unused")
    private ResourceMatrixValue(){}

    private ResourceMatrixValue(Set<EquipmentId> resourceIds,
                                AuxiliaryWorkTime auxiliaryWorkTime){
        this.resourceIds = resourceIds;
        this.auxiliaryWorkTime = auxiliaryWorkTime;
    }

    public static ResourceMatrixValue create(Set<EquipmentId> resourceIds,
                                             AuxiliaryWorkTime auxiliaryWorkTime){
        return new ResourceMatrixValue(resourceIds, auxiliaryWorkTime);
    }

    public Set<EquipmentId> getResourceIds() {
        return new HashSet<>(resourceIds);
    }

    public AuxiliaryWorkTime getAuxiliaryWorkTime() {
        return auxiliaryWorkTime.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceMatrixValue)) return false;
        ResourceMatrixValue that = (ResourceMatrixValue) o;
        return Objects.equals(resourceIds, that.resourceIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceIds);
    }

    @Override
    public String toString() {
        return "ResourceMatrixValue{" +
                "resourceIds=" + resourceIds +
                ", auxiliaryWorkTime=" + auxiliaryWorkTime +
                '}';
    }

    @Override
    public ResourceMatrixValue clone() {
        try {
            ResourceMatrixValue clone = (ResourceMatrixValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
