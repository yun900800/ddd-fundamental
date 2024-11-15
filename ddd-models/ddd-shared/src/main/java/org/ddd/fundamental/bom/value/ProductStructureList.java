package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品结构
 */
@MappedSuperclass
@Embeddable
public class ProductStructureList implements ValueObject,Cloneable {

    /**
     * 产品id
     */
    private MaterialId productId;

    /**
     * 零部件id
     */
    private List<MaterialId> sparePartIds;

    /**
     * 原材料ids
     */
    private List<MaterialId> rawMaterialIds;

    @SuppressWarnings("unused")
    private ProductStructureList(){}

    private ProductStructureList(MaterialId productId,
                                 List<MaterialId> sparePartIds,
                                 List<MaterialId> rawMaterialIds){
        this.productId = productId;
        this.sparePartIds = sparePartIds;
        this.rawMaterialIds = rawMaterialIds;
    }

    public static ProductStructureList create(MaterialId productId,
                                              List<MaterialId> sparePartIds,
                                              List<MaterialId> rawMaterialIds){
        return new ProductStructureList(productId,sparePartIds,rawMaterialIds);
    }

    public MaterialId getProductId() {
        return productId;
    }

    public List<MaterialId> getSparePartIds() {
        return new ArrayList<>(
                sparePartIds
        );
    }

    public List<MaterialId> getRawMaterialIds() {
        return new ArrayList<>(rawMaterialIds);
    }
}
