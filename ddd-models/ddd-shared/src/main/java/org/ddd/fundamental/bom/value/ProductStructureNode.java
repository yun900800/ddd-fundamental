package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 产品结构节点
 */
@MappedSuperclass
@Embeddable
public class ProductStructureNode implements ValueObject, Cloneable {

    private MaterialMaster materialMaster;

    private int qty;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;


    private ProductStructureNode(){
    }

    private ProductStructureNode(MaterialMaster materialMaster,
                                 int qty,MaterialType materialType){
        this.materialMaster = materialMaster;
        this.qty = qty;
        this.materialType = materialType;
    }

    public static ProductStructureNode create(MaterialMaster materialMaster,
                                              int qty,MaterialType materialType){
        return new ProductStructureNode(materialMaster,qty,materialType);
    }

    public MaterialMaster getMaterialMaster() {
        return materialMaster.clone();
    }

    public int getQty() {
        return qty;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStructureNode)) return false;
        ProductStructureNode that = (ProductStructureNode) o;
        return qty == that.qty && Objects.equals(materialMaster, that.materialMaster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialMaster, qty);
    }

    @Override
    public String toString() {
        return "ProductStructureNode{" +
                "materialMaster=" + materialMaster +
                ", qty=" + qty +
                '}';
    }

    @Override
    public ProductStructureNode clone() {
        try {
            ProductStructureNode clone = (ProductStructureNode) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
