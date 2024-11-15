package org.ddd.fundamental.bom.domain.model;

import org.ddd.fundamental.bom.ProductStructureDataId;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.value.MaterialId;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_product_structure_data")
public class ProductStructureData extends AbstractAggregateRoot<ProductStructureDataId> {

    @Embedded
    private MaterialIdNode materialIdNode;


    @Override
    public long created() {
        return 0;
    }

    @SuppressWarnings("unused")
    private ProductStructureData(){
    }

    private ProductStructureData(MaterialIdNode materialIdNode){
        super(ProductStructureDataId.randomId(ProductStructureDataId.class));
        this.materialIdNode = materialIdNode;
    }

    public static ProductStructureData create(MaterialIdNode materialIdNode){
        return new ProductStructureData(materialIdNode);
    }

    public MaterialIdNode getMaterialIdNode() {
        return materialIdNode;
    }

    public MaterialId getProductId() {
        return materialIdNode.getProductId();
    }
}
