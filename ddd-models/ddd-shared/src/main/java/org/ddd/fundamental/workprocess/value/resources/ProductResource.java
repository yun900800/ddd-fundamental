package org.ddd.fundamental.workprocess.value.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.jackson.ProductResourceDeserializer;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 生产资源
 * @param <ID>
 */
@Embeddable
@MappedSuperclass
@JsonDeserialize(using = ProductResourceDeserializer.class)
public class ProductResource<ID extends DomainObjectId> implements ValueObject, Cloneable {

    private ID id;

    private ProductResourceType resourceType;

    private ChangeableInfo resource;

    private ProductResource(){}

    private ProductResource(ID id, ProductResourceType resourceType,
                            ChangeableInfo resource){
        this.id = id;
        this.resourceType = resourceType;
        this.resource = resource;
    }

    public static <ID extends DomainObjectId> ProductResource create(ID id, ProductResourceType resourceType,
                                             ChangeableInfo resource){
        return new ProductResource(id,resourceType,resource);
    }

    public ID getId() {
        return id;
    }

    public ProductResourceType getResourceType() {
        return resourceType;
    }

    public ChangeableInfo getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductResource)) return false;
        ProductResource<?> that = (ProductResource<?>) o;
        return Objects.equals(id, that.id) && resourceType == that.resourceType && Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resourceType, resource);
    }

    @Override
    public String toString() {
        return "ProductResource{" +
                "id=" + id +
                ", resourceType=" + resourceType +
                ", resource=" + resource +
                '}';
    }

    @Override
    public ProductResource<ID> clone() {
        try {
            ProductResource clone = (ProductResource) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
