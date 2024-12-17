package org.ddd.fundamental.shared.api.bom;

import org.ddd.fundamental.material.value.MaterialId;

public class BomIdDTO {
    @Override
    public String toString() {
        return "BomIdDTO{" +
                "productId=" + productId +
                '}';
    }

    public MaterialId getProductId() {
        return productId;
    }

    private MaterialId productId;

    protected BomIdDTO(){}

    public BomIdDTO(MaterialId id){
        this.productId = id;
    }

}
