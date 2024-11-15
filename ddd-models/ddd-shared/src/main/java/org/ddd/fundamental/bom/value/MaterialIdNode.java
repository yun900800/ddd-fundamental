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

    @SuppressWarnings("unused")
    private MaterialIdNode(){
    }

    private MaterialIdNode(MaterialId current,
                           MaterialId parent){
        this.current = current;
        this.parent = parent;
    }

    public static MaterialIdNode create(MaterialId current,
                                        MaterialId parent){
        return new MaterialIdNode(current, parent);
    }

    public MaterialId getCurrent() {
        return current;
    }

    public MaterialId getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialIdNode)) return false;
        MaterialIdNode that = (MaterialIdNode) o;
        return Objects.equals(current, that.current) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, parent);
    }

    @Override
    public String toString() {
        return "MaterialIdNode{" +
                "current=" + current +
                ", parent=" + parent +
                '}';
    }
}
