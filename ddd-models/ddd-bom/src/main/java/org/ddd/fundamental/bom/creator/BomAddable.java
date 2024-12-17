package org.ddd.fundamental.bom.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BomAddable implements DataAddable {

    private final ProductStructureDataRepository repository;

    private List<ProductStructureData> structureDataList;
    @Autowired
    public BomAddable(ProductStructureDataRepository repository){
        this.repository = repository;
    }



    public static ProductStructure<ProductStructureNode> createProductStructure(){
        ProductStructure structure = CollectionUtils.random(new ArrayList<>(BomCreator.createProductStructures(5)));
        Set<ProductStructure<ProductStructureNode>> spares = BomCreator.createSparePartsStructures(10);
        Set<ProductStructure<ProductStructureNode>> rawMaterials = BomCreator.createRawMaterialStructures(20);
        log.info("spares size is {}",spares.size());
        for (int i = 0 ; i< 4; i++) {
            ProductStructure spare = CollectionUtils.random(new ArrayList<>(spares));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            structure.addStructure(spare);
        }
        return structure;
    }

    public static List<ProductStructure<ProductStructureNode>> createProductStructures(int size) {
        List<ProductStructure<ProductStructureNode>> productStructures = new ArrayList<>();
        for (int i = 0 ; i< size; i++) {
            productStructures.add(createProductStructure());
        }
        return productStructures;
    }
    @Override
    @Transactional
    public void execute() {
        List<ProductStructure<ProductStructureNode>> structures = createProductStructures(5);
        structureDataList = structures.stream().map(v->v.toMaterialIdList())
                .flatMap(s->s.stream()).map(v->ProductStructureData.create(v)).collect(Collectors.toList());
        repository.persistAll(structureDataList);
        log.info("开始创建bom数据");
    }
}
