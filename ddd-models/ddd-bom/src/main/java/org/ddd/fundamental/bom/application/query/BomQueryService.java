package org.ddd.fundamental.bom.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.bom.value.ProductStructureNodeList;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.ddd.fundamental.shared.api.bom.ProductStructureDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Transactional(readOnly = true)
@Service
public class BomQueryService {
    private final ProductStructureDataRepository structureDataRepository;

    private final MaterialClient materialClient;
    
    private final RedisStoreManager manager;

    public BomQueryService(ProductStructureDataRepository structureDataRepository,
                             MaterialClient materialClient,
                           RedisStoreManager manager){
        this.structureDataRepository = structureDataRepository;
        this.materialClient = materialClient;
        this.manager = manager;
    }

    public List<MaterialDTO> materialsByMaterialType(MaterialType materialType){
        return materialClient.materialsByMaterialType(materialType);
    }

    public List<MaterialDTO> materialsByIds(List<String> ids){
        return materialClient.materialsByIds(ids);
    }

    public List<BomIdDTO> allBomIds(){
        return structureDataRepository.allBomIds();
    }

    public ProductStructureDTO getProductStructure(MaterialId productId){
        ProductStructureDTO productStructureDTO = this.manager.fetchDataFromCache(productId,ProductStructureDTO.class);
        if (null != productStructureDTO) {
            log.info("fetch data from redis");
            return productStructureDTO;
        }
        List<ProductStructureData> productStructureData = structureDataRepository.findByProductId(productId);
        List<MaterialIdNode<ProductStructureNode>> nodeList = productStructureData
                .stream().map(v->v.getMaterialIdNode()).collect(Collectors.toList());
        Map<MaterialType, List<MaterialIdNode<ProductStructureNode>>> objMap = toMap(nodeList);
        MaterialIdNode<ProductStructureNode> productNode = objMap.get(MaterialType.PRODUCTION).get(0);
        List<MaterialIdNode<ProductStructureNode>> spareNodes = objMap.get(MaterialType.WORKING_IN_PROGRESS);
        List<MaterialIdNode<ProductStructureNode>> rawNodes = objMap.get(MaterialType.RAW_MATERIAL);
        ProductStructureNodeList<ProductStructureNode> list =
                ProductStructureNodeList.create(
                        productNode,spareNodes,rawNodes
                );
        log.info("calculate structure from data");
        ProductStructure<ProductStructureNode> productStructure = list.toProductStructures();
        productStructureDTO = ProductStructureDTO.create(productStructure);
        this.manager.storeDataToCache(productStructureDTO);
        return productStructureDTO;
    }

    private Map<MaterialType, List<MaterialIdNode<ProductStructureNode>>> toMap(List<MaterialIdNode<ProductStructureNode>> dataList){
        return dataList.stream().collect(groupingBy(v->v.getData().getMaterialType()));
    }

}
