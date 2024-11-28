package org.ddd.fundamental.core.tenant;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class TenantInfo implements ValueObject,Cloneable {

    private TenantId tenantId;

    private FactoryId factoryId;

    @SuppressWarnings("unused")
    protected TenantInfo(){}

    private TenantInfo(TenantId tenantId,FactoryId factoryId){
        this.tenantId = tenantId;
        this.factoryId = factoryId;
    }

    public static TenantInfo create(TenantId tenantId,FactoryId factoryId){
        return new TenantInfo(tenantId, factoryId);
    }


    public TenantId getTenantId() {
        return tenantId;
    }

    public FactoryId getFactoryId() {
        return factoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantInfo)) return false;
        TenantInfo that = (TenantInfo) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(factoryId, that.factoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, factoryId);
    }

    @Override
    public String toString() {
        return "TenantInfo{" +
                "tenantId=" + tenantId +
                ", factoryId=" + factoryId +
                '}';
    }

    @Override
    public TenantInfo clone() {
        try {
            TenantInfo clone = (TenantInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
