package org.ddd.fundamental.bom.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.BomAppTest;
import org.ddd.fundamental.bom.creator.BomCreator;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ProductStructureDataRepositoryTest extends BomAppTest {

    @Autowired
    private ProductStructureDataRepository repository;

    @Test
    public void createBomData() {
        ProductStructure structure = CollectionUtils.random(new ArrayList<>(BomCreator.createProductStructures(5)));
        Set<ProductStructure<ProductStructureNode>> spares = BomCreator.createSparePartsStructures(10);
        Set<ProductStructure<ProductStructureNode>> rawMaterials = BomCreator.createRawMaterialStructures(20);
        log.info("spares size is {}",spares.size());
        for (int i = 0 ; i< 4; i++) {
            ProductStructure<ProductStructureNode> spare = CollectionUtils.random(new ArrayList<>(spares));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            structure.addStructure(spare);
        }
        List<MaterialIdNode<ProductStructureNode>> materialIdNodeList = structure.toMaterialIdList();
        List<ProductStructureData> data = materialIdNodeList.stream().map(v->
                ProductStructureData.create(v)).collect(Collectors.toList());
        repository.saveAll(data);
    }

    @Test
    public void testAllBomProductIds(){
        List<BomIdDTO> bomIdDTOS = repository.allBomIds();
        log.info("bomIdDTOS is {}",bomIdDTOS);

        List<ProductStructureData> dataList = repository.findByProductId(bomIdDTOS.get(0).getProductId());
        log.info("dataList is {}",dataList);
    }
}
