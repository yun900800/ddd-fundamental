package org.ddd.fundamental.bom.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.bom.helper.BomHelper;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class BomCommandService {

    private final ProductStructureDataRepository structureDataRepository;

    private final BomQueryService bomQueryService;

    public BomCommandService(ProductStructureDataRepository structureDataRepository,
                             BomQueryService bomQueryService){
        this.structureDataRepository = structureDataRepository;
        this.bomQueryService = bomQueryService;
    }


    /**
     * 创建一个产品Bom
     * @param productId
     */
    public void createProductBom(MaterialId productId, int spareSize, int rawSize){
        List<MaterialDTO> products = bomQueryService
                .materialsByIds(Arrays.asList(productId.toUUID()));
        log.info("products is {}",products);
        List<MaterialDTO> spares = bomQueryService
                .materialsByMaterialType(MaterialType.WORKING_IN_PROGRESS);
        List<MaterialDTO> rawMaterials = bomQueryService
                .materialsByMaterialType(MaterialType.RAW_MATERIAL);
        ProductStructure<ProductStructureNode> productStructure = BomHelper.createProductStructure(
                products.get(0), MaterialType.PRODUCTION
        );
        Set<ProductStructure<ProductStructureNode>> spareStructures = BomHelper.createProductStructuresAndMaterialType(spares.size(),
                spares, MaterialType.WORKING_IN_PROGRESS
        );

        Set<ProductStructure<ProductStructureNode>> rawStructures = BomHelper.createProductStructuresAndMaterialType(rawMaterials.size(),
                rawMaterials, MaterialType.RAW_MATERIAL
        );
        for (int i = 0 ; i< spareSize; i++) {
            ProductStructure<ProductStructureNode> spare = CollectionUtils.random(new ArrayList<>(spareStructures));
            for (int j = 0 ; j < rawSize; j++) {
                spare.addStructure(CollectionUtils.random(new ArrayList<>(rawStructures)));
            }
            productStructure.addStructure(spare);
        }
        List<MaterialIdNode<ProductStructureNode>> list = productStructure.toMaterialIdList();
        List<ProductStructureData> structureDataList = list.stream().map(v->ProductStructureData.create(v)).collect(Collectors.toList());
        structureDataRepository.persistAll(structureDataList);
    }
}
