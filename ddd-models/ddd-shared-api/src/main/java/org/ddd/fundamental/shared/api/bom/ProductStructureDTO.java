package org.ddd.fundamental.shared.api.bom;

import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.material.value.MaterialId;


public class ProductStructureDTO extends AbstractDTO<MaterialId> {

    private ProductStructure<MaterialIdNode<ProductStructureNode>> productStructure;

    protected ProductStructureDTO(){}

    private ProductStructureDTO(ProductStructure<MaterialIdNode<ProductStructureNode>> productStructure){
        super(productStructure.getId());
        this.productStructure = productStructure;
    }

    public static ProductStructureDTO create(ProductStructure<MaterialIdNode<ProductStructureNode>> productStructure){
        return new ProductStructureDTO(productStructure);
    }

    @Override
    public MaterialId id() {
        return super.id;
    }

    public ProductStructure<MaterialIdNode<ProductStructureNode>> getProductStructure() {
        return productStructure;
    }

    @Override
    public String toString() {
        return "ProductStructureDTO{" +
                "productStructure=" + productStructure +
                ", id=" + id +
                '}';
    }
}
