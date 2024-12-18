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
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
public class BomQueryService {
    private final ProductStructureDataRepository structureDataRepository;

    private final MaterialClient materialClient;

    public BomQueryService(ProductStructureDataRepository structureDataRepository,
                             MaterialClient materialClient){
        this.structureDataRepository = structureDataRepository;
        this.materialClient = materialClient;
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

    /**
     * 获取产品节点
     * @param nodeList
     * @return
     */
    private MaterialIdNode<ProductStructureNode> productNode(List<MaterialIdNode<ProductStructureNode>> nodeList){
        for (MaterialIdNode<ProductStructureNode> node:nodeList) {
            if (node.getParent() == null) {
                return node;
            }
        }
        throw new RuntimeException("数据存在错误,bom树没有产品根节点");
    }

    private List<MaterialIdNode<ProductStructureNode>> spareNodes(List<MaterialIdNode<ProductStructureNode>> nodeList,
                                                                  MaterialIdNode<ProductStructureNode> productNode){
        List<MaterialIdNode<ProductStructureNode>> spares = new ArrayList<>();
        for (MaterialIdNode<ProductStructureNode> node:nodeList) {
            if (null != node.getParent() && node.getParent().equals(productNode.getCurrent())) {
                spares.add(node);
            }
        }
        return spares;
    }

    private List<MaterialIdNode<ProductStructureNode>> rawNodes(List<MaterialIdNode<ProductStructureNode>> nodeList,
                                                                MaterialIdNode<ProductStructureNode> productNode,
                                                                List<MaterialIdNode<ProductStructureNode>> spareNodes){
        nodeList.remove(productNode);
        nodeList.removeAll(spareNodes);
        List<MaterialIdNode<ProductStructureNode>> result = new ArrayList<>();
        for (MaterialIdNode<ProductStructureNode> node:nodeList) {
            result.add(node);
        }
        return result;
    }

    public ProductStructure<MaterialIdNode<ProductStructureNode>> getProductStructure(MaterialId productId){
        List<ProductStructureData> productStructureData = structureDataRepository.findByProductId(productId);
        List<MaterialIdNode<ProductStructureNode>> nodeList = productStructureData
                .stream().map(v->v.getMaterialIdNode()).collect(Collectors.toList());
        MaterialIdNode<ProductStructureNode> productNode = productNode(nodeList);
        List<MaterialIdNode<ProductStructureNode>> spareNodes = spareNodes(nodeList,productNode);
        List<MaterialIdNode<ProductStructureNode>> rawNodes = rawNodes(nodeList,productNode,spareNodes);
        ProductStructureNodeList<ProductStructureNode> list =
                ProductStructureNodeList.create(
                        productNode,spareNodes,rawNodes
                );
        return list.toProductStructure();
    }

}
