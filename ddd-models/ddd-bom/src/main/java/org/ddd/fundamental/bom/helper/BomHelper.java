package org.ddd.fundamental.bom.helper;

import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BomHelper {


    public static ProductStructure<ProductStructureNode> createProductStructure(MaterialDTO materialDTO, MaterialType materialType){
        ProductStructureNode node =
                ProductStructureNode.create(materialDTO.getMaterialMaster(),1);
        MaterialId productId = materialDTO.id();
        return new ProductStructure<>(productId,node, materialType);
    }

    public static Set<ProductStructure<ProductStructureNode>> createProductStructuresAndMaterialType(int size,
                                                                                                     List<MaterialDTO> materialDTOList,
                                                                                                     MaterialType materialType){
        Set<ProductStructure<ProductStructureNode>> productStructures = new HashSet<>();

        for (int i = 0 ; i < size; i++) {
            MaterialDTO materialDTO = materialDTOList.get(i);
            productStructures.add(createProductStructure(materialDTO,materialType));
        }
        return productStructures;
    }

}
