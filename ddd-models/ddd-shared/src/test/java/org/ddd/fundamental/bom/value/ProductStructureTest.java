package org.ddd.fundamental.bom.value;

import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.creator.BomCreator;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.tuple.ThreeTuple;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class ProductStructureTest {



    @Test
    public void testCreateProductStructure(){
        ProductStructureNode node =
                ProductStructureNode.create(MaterialMaster.create(
                        "dell-xmp","戴尔电脑","dell-xmp-1","台"
                ),1, MaterialType.PRODUCTION);
        MaterialId productId = MaterialId.randomId(MaterialId.class);
        ProductStructure<ProductStructureNode> structure = new ProductStructure<>(productId,node,
                MaterialType.PRODUCTION);
        Assert.assertEquals(structure.getChildren().size(),0);
        Assert.assertEquals(structure.getId(),productId);
    }

    @Test
    public void testCreateBuildProducts() {
        ProductStructure structure = CollectionUtils.random(new ArrayList<>(BomCreator.createProductStructures(5)));
        Set<ProductStructure<ProductStructureNode>> spares = BomCreator.createSparePartsStructures(10);
        log.info("spares size is {}",spares.size());
        for (int i = 0 ; i< 4; i++) {
            structure.addStructure(CollectionUtils.random(new ArrayList<>(spares)));
        }
        Assert.assertTrue(structure.getChildren().size() <=4);
        System.out.println(structure);
    }

    @Test
    public void testFullStructureProducts(){
        ProductStructure<ProductStructureNode> structure = createStructure().first;
        System.out.println(structure);
        List<MaterialIdNode<ProductStructureNode>> results = structure.toMaterialIdList();
        System.out.println(results);
        Multimap<MaterialId, MaterialId> multimap = structure.productIdToNodeId();
        System.out.println(multimap);
    }

    private ThreeTuple<ProductStructure<ProductStructureNode>,MaterialId,MaterialId> createStructure(){
        ProductStructure<ProductStructureNode> structure = CollectionUtils.random(new ArrayList<>(BomCreator.createProductStructures(5)));
        Set<ProductStructure<ProductStructureNode>> spares = BomCreator.createSparePartsStructures(10);
        Set<ProductStructure<ProductStructureNode>> rawMaterials = BomCreator.createRawMaterialStructures(20);
        log.info("spares size is {}",spares.size());
        MaterialId spareId = null;
        MaterialId rawId = null;
        for (int i = 0 ; i< 4; i++) {
            ProductStructure spare = CollectionUtils.random(new ArrayList<>(spares));
            spareId = spare.getId();
            ProductStructure rawMaterial = CollectionUtils.random(new ArrayList<>(rawMaterials));
            rawId = rawMaterial.getId();
            spare.addStructure(rawMaterial);
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            spare.addStructure(CollectionUtils.random(new ArrayList<>(rawMaterials)));
            structure.addStructure(spare);
        }
        return Tuple.tuple(structure,spareId,rawId);
    }

    @Test
    public void testSearchById(){

        ThreeTuple<ProductStructure<ProductStructureNode>,MaterialId,MaterialId> threeTuple = createStructure();
        ProductStructure<ProductStructureNode> structure = threeTuple.first;
        ProductStructure<ProductStructureNode> searchResult = structure.searchById(threeTuple.second);
        ProductStructure<ProductStructureNode> searchResult1 = structure.searchById(threeTuple.third);
        Assert.assertNotNull(searchResult);
        Assert.assertNotNull(searchResult1);
    }

    @Test
    public void testToProductStructureNodeList(){
        ProductStructure<ProductStructureNode> structure = CollectionUtils.random(new ArrayList<>(BomCreator.createProductStructures(5)));
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
        System.out.println(structure);
        ProductStructureNodeList<ProductStructureNode> structureList = structure.toProductStructureNodeList();

        ProductStructure<MaterialIdNode<ProductStructureNode>> structure1 = structureList.toProductStructure();
        System.out.println(structure1);

        ProductStructure<ProductStructureNode> structure2 = structureList.toProductStructures();
        System.out.println(structure2);
    }
}
