package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Embeddable
public class BomValue implements ValueObject,Cloneable {


    @Embedded
    private ProductStructureList productStructureList;

    /**
     * 工序id
     */
    private CraftsmanShipId craftsmanShipId;


    @Override
    public BomValue clone() {
        try {
            return (BomValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
