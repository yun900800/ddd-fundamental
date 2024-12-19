package org.ddd.fundamental.common.group;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class GroupUtilsTest {

    private static List<MaterialDTO> create(){
        return Arrays.asList(
                MaterialDTO.create(MaterialType.PRODUCTION,"产品测试"),
                MaterialDTO.create(MaterialType.PRODUCTION,"产品测试1"),
                MaterialDTO.create(MaterialType.RAW_MATERIAL,"原料1"),
                MaterialDTO.create(MaterialType.RAW_MATERIAL,"原料2"),
                MaterialDTO.create(MaterialType.WORKING_IN_PROGRESS,"在制品1"),
                MaterialDTO.create(MaterialType.WORKING_IN_PROGRESS,"在制品2")
        );
    }

    @Test
    public void testGroup(){
        Map<MaterialType, Collection<MaterialDTO>> objMap = GroupUtils.group(create());
        Assert.assertEquals(3,objMap.size());
        Collection<MaterialDTO> collection = objMap.get(MaterialType.PRODUCTION);
        Assert.assertEquals(2,collection.size());
        //为什么Collection 可以转化为List
        List<MaterialDTO> dataList = (List<MaterialDTO>)collection;
        System.out.println(dataList);
    }
}

class MaterialDTO implements GroupKey<MaterialType>{
    private MaterialId id;

    private MaterialType materialType;

    private String name;

    private MaterialDTO(MaterialType materialType,
                       String name){
        this.id = MaterialId.randomId(MaterialId.class);
        this.materialType = materialType;
        this.name = name;
    }

    public static MaterialDTO create(MaterialType materialType,
                                     String name){
        return new MaterialDTO(materialType, name);
    }

    public MaterialId getId() {
        return id;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
                "id=" + id +
                ", materialType=" + materialType +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public MaterialType key() {
        return materialType;
    }
}
