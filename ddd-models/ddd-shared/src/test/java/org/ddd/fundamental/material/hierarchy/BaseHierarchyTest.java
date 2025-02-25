package org.ddd.fundamental.material.hierarchy;


import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.ddd.fundamental.material.MaterialCharacter;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BaseHierarchyTest{


    private static MaterialHierarchy createRoot(){
        MaterialId current = MaterialId.randomId(MaterialId.class);
        MaterialMaster materialMaster = MaterialMaster.create(
                NameDescInfo.create("物料根节点","这是某类有结构的物料的根节点"),
                MaterialCharacter.create("root","spec-root","个")
        );
        MaterialHierarchy root = new MaterialHierarchy(
                current,
                null,
                materialMaster
        );
        return root;
    }

    private static Set<MaterialHierarchy> createMultipleData(MaterialId current, int size){
        Set<MaterialHierarchy> result = new HashSet<>();
        for (int i = 0 ; i< size;i++) {
            MaterialId levelOne = MaterialId.randomId(MaterialId.class);
            MaterialMaster materialMasterLevelOne = MaterialMaster.create(
                    NameDescInfo.create("物料第一层级第"+(i+1)+"节点","这是某类有结构的物料的第一层级第"+(i+1)+"节点"),
                    MaterialCharacter.create("level-one","spec-level-one","个")
            );
            MaterialHierarchy firstLevelData = new MaterialHierarchy(
                    levelOne,
                    current,
                    materialMasterLevelOne
            );
            result.add(firstLevelData);
        }
        return result;
    }

    @Test
    public void testAddSingleData(){
        MaterialId current = MaterialId.randomId(MaterialId.class);
        MaterialMaster materialMaster = MaterialMaster.create(
                NameDescInfo.create("物料根节点","这是某类有结构的物料的根节点"),
                MaterialCharacter.create("root","spec-root","个")
        );
        MaterialHierarchy root = new MaterialHierarchy(
                current,
                null,
                materialMaster
        );
        MaterialId levelOne = MaterialId.randomId(MaterialId.class);
        MaterialMaster materialMasterLevelOne = MaterialMaster.create(
                NameDescInfo.create("物料第一层级第一节点","这是某类有结构的物料的第一层级第一节点"),
                MaterialCharacter.create("level-one","spec-level-one","个")
        );
        MaterialHierarchy firstLevelData = new MaterialHierarchy(
                levelOne,
                current,
                materialMasterLevelOne
        );
        root.addNode(firstLevelData);
        Assert.assertEquals(root.getChildren().size(),1,0);
        Assert.assertEquals(root.getParentId(),null);
        Assert.assertEquals(root.getData(),materialMaster);
        Assert.assertEquals(root.getId(),current);

        for (MaterialHierarchy data: root.getChildren()) {
            Assert.assertEquals(data.getChildren().size(),0,0);
            Assert.assertEquals(data.getParentId(),current);
            Assert.assertEquals(data.getData(),materialMasterLevelOne);
            Assert.assertEquals(data.getId(),levelOne);
        }

    }

    @Test
    public void testAddMultipleDataOrRemoveData(){
        MaterialHierarchy root = createRoot();
        int size = 8;
        MaterialHierarchy node = null;
        for (MaterialHierarchy data: createMultipleData(root.getId(),size)) {
            root.addNode(data);
            node = data;
        }
        Assert.assertEquals(root.getChildren().size(),size,0);
        root.removeNode(node);
        Assert.assertEquals(root.getChildren().size(),size-1,0);
        root.clearNode();
        Assert.assertEquals(root.getChildren().size(),0,0);
    }

    //测试能够迭代数据
    @Test
    public void testTraverseTree(){
        MaterialHierarchy root = createRoot();
        int size = 4;
        for (MaterialHierarchy data: createMultipleData(root.getId(),size)) {
            root.addNode(data);
        }

        final TraverseNode<MaterialHierarchy,String> fn = (p1,p2) -> {
            log.info("this is {}",p2.getData());
            return p1;
        };
        root.traverse(fn,root,"");
    }
}

class MaterialHierarchy extends BaseHierarchy<MaterialId, MaterialMaster, MaterialHierarchy> {
    public MaterialHierarchy(MaterialId id, MaterialId parentId, MaterialMaster data) {
        super(id, parentId, data);
    }

    @Override
    void canAddedToHierarchy(MaterialHierarchy node) {

    }

    @Override
    void canRemovedToHierarchy(MaterialHierarchy node) {

    }


}
