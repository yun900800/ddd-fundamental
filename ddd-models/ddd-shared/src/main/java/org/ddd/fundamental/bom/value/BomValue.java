package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class BomValue implements ValueObject,Cloneable {


    @Embedded
    private ProductStructureNodeList nodeList;

    /**
     * 工艺id
     */
    private CraftsmanShipId craftsmanShipId;

    @SuppressWarnings("unused")
    private BomValue(){}

    private BomValue(ProductStructureNodeList nodeList,
                     CraftsmanShipId craftsmanShipId){
        this.craftsmanShipId = craftsmanShipId;
        this.nodeList = nodeList;
    }

    public ProductStructureNodeList getNodeList() {
        return nodeList.clone();
    }

    public CraftsmanShipId getCraftsmanShipId() {
        return craftsmanShipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BomValue)) return false;
        BomValue bomValue = (BomValue) o;
        return Objects.equals(nodeList, bomValue.nodeList) && Objects.equals(craftsmanShipId, bomValue.craftsmanShipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeList, craftsmanShipId);
    }

    @Override
    public BomValue clone() {
        try {
            return (BomValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "BomValue{" +
                "nodeList=" + nodeList +
                ", craftsmanShipId=" + craftsmanShipId +
                '}';
    }
}
