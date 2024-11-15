package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class MaterialIdNode implements ValueObject {

    private MaterialId current;

    private MaterialId parent;

    private MaterialId productId;

    @SuppressWarnings("unused")
    private MaterialIdNode(){
    }

    private MaterialIdNode(MaterialId current,
                           MaterialId parent,
                           MaterialId productId){
        this.current = current;
        this.parent = parent;
        this.productId = productId;
    }

    public static MaterialIdNode create(MaterialId current,
                                        MaterialId parent,
                                        MaterialId productId){
        return new MaterialIdNode(current, parent,productId);
    }

    public MaterialId getCurrent() {
        return current;
    }

    public MaterialId getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "MaterialIdNode{" +
                "current=" + current +
                ", parent=" + parent +
                ", productId=" + productId +
                '}';
    }

    public MaterialId getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialIdNode)) return false;
        MaterialIdNode that = (MaterialIdNode) o;
        return Objects.equals(current, that.current) && Objects.equals(parent, that.parent) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, parent, productId);
    }
}
