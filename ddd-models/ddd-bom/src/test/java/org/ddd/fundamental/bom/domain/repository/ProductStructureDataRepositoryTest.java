package org.ddd.fundamental.bom.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.BomAppTest;
import org.ddd.fundamental.bom.creator.BomCreator;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
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
        Set<ProductStructure> spares = BomCreator.createSparePartsStructures(10);
        Set<ProductStructure> rawMaterials = BomCreator.createRawMaterialStructures(20);
        log.info("spares size is {}",spares.size());
        for (int i = 0 ; i< 4; i++) {
            ProductStructure spare = CollectionUtils.random(new ArrayList<>(spares));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            structure.addStructure(spare);
        }
        List<MaterialIdNode> materialIdNodeList = structure.toMaterialIdList();
        List<ProductStructureData> data = materialIdNodeList.stream().map(v->
                ProductStructureData.create(v)).collect(Collectors.toList());
        repository.saveAll(data);
    }
}
