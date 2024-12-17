package org.ddd.fundamental.bom.creator;

import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.utils.CollectionUtils;

import java.util.*;


public class BomCreator {

    public static ProductStructure<ProductStructureNode> createProductStructure(String productName, MaterialType materialType){
        ProductStructureNode node =
                ProductStructureNode.create(MaterialMaster.create(
                        "dell-xmp",productName,"dell-xmp-1","台"
                ),1);
        MaterialId productId = MaterialId.randomId(MaterialId.class);
        return new ProductStructure<>(productId,node, materialType);
    }


    public static List<String> productNames(){
        return Arrays.asList("电脑","汽车","玩具");
    }

    public static List<String> sparePartsNames(){
        return Arrays.asList("主板","鼠标","显示器","CPU","GPU","电源","轮胎",
                "发动机","底盘","车门","玻璃","汽车软件","汽车后备箱");
    }

    public static List<String> rawMaterialNames() {
        return Arrays.asList("螺丝","锡膏","铜线","电缆","铝箔","金箔","塑料壳","卡丝","塑料钉","螺帽");
    }

    public static Set<ProductStructure<ProductStructureNode>> createProductStructures(int size){
        Set<ProductStructure<ProductStructureNode>> productStructures = new HashSet<>();

        for (int i = 0 ; i < size; i++) {
            productStructures.add(createProductStructure(CollectionUtils.random(productNames()),MaterialType.PRODUCTION));
        }
        return productStructures;
    }

    public static Set<ProductStructure<ProductStructureNode>> createSparePartsStructures(int size){
        Set<ProductStructure<ProductStructureNode>> productStructures = new HashSet<>();

        for (int i = 0 ; i < size; i++) {
            productStructures.add(createProductStructure(CollectionUtils.random(sparePartsNames()),MaterialType.WORKING_IN_PROGRESS));
        }
        return productStructures;
    }

    public static Set<ProductStructure<ProductStructureNode>> createRawMaterialStructures(int size){
        Set<ProductStructure<ProductStructureNode>> productStructures = new HashSet<>();

        for (int i = 0 ; i < size; i++) {
            productStructures.add(createProductStructure(CollectionUtils.random(rawMaterialNames()),MaterialType.RAW_MATERIAL));
        }
        return productStructures;
    }
}
