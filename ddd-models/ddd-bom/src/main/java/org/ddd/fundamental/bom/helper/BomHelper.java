package org.ddd.fundamental.bom.helper;

import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BomHelper {


    public static ProductStructure<ProductStructureNode> createProductStructure(MaterialDTO materialDTO, MaterialType materialType){
        ProductStructureNode node =
                ProductStructureNode.create(materialDTO.getMaterialMaster(),1,materialType);
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

    public static List<Integer> spareCount(){
        return Arrays.asList(2,3,4,5);
    }

    public static List<Integer> rawCount(){
        return Arrays.asList(4,5,6,8);
    }

}
