package org.ddd.fundamental.material.hierarchy;


import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.ddd.fundamental.material.MaterialCharacter;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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

    private static MaterialHierarchyWithType createRootWithType(){
        MaterialId current = MaterialId.randomId(MaterialId.class);
        MaterialMasterWithType materialMaster = MaterialMasterWithType.create(
                MaterialType.PRODUCTION,
                NameDescInfo.create("物料根节点","这是某类有结构的物料的根节点"),
                MaterialCharacter.create("root","spec-root","个")
        );
        MaterialHierarchyWithType root = new MaterialHierarchyWithType(
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

    private static Set<MaterialHierarchyWithType> createSpares(MaterialId current, int size){
        Set<MaterialHierarchyWithType> result = new HashSet<>();
        for (int i = 0 ; i< size;i++) {
            MaterialId levelOne = MaterialId.randomId(MaterialId.class);
            MaterialMasterWithType materialMasterLevelOne = MaterialMasterWithType.create(
                    MaterialType.WORKING_IN_PROGRESS,
                    NameDescInfo.create("物料第一层级第"+(i+1)+"节点","这是某类有结构的物料的第一层级第"+(i+1)+"节点"),
                    MaterialCharacter.create("level-one","spec-level-one","个")
            );
            MaterialHierarchyWithType firstLevelData = new MaterialHierarchyWithType(
                    levelOne,
                    current,
                    materialMasterLevelOne
            );
            result.add(firstLevelData);
        }
        return result;
    }

    private static Set<MaterialHierarchyWithType> createRaws(MaterialId current, int size){
        Set<MaterialHierarchyWithType> result = new HashSet<>();
        for (int i = 0 ; i< size;i++) {
            MaterialId levelOne = MaterialId.randomId(MaterialId.class);
            MaterialMasterWithType materialMasterLevelOne = MaterialMasterWithType.create(
                    MaterialType.RAW_MATERIAL,
                    NameDescInfo.create("物料第一层级第"+(i+1)+"节点","这是某类有结构的物料的第一层级第"+(i+1)+"节点"),
                    MaterialCharacter.create("level-one","spec-level-one","个")
            );
            MaterialHierarchyWithType firstLevelData = new MaterialHierarchyWithType(
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

    @Test
    public void testAddDataWithType(){
        MaterialHierarchyWithType root = createRootWithType();
        Set<MaterialHierarchyWithType> spared = createSpares(root.getId(),4);
        for (MaterialHierarchyWithType data: spared) {
            root.addNode(data);
        }

        Set<MaterialHierarchyWithType> rawMaterials = createRaws(root.getId(),5);
        for (MaterialHierarchyWithType data: rawMaterials) {
            root.addNode(data);
        }
        Assert.assertEquals(root.getChildren().size(),9,0);

        final TraverseNode<MaterialHierarchyWithType,MaterialContainer> fn = (p1,p2) -> {
            log.info("this is {}",p2.getData());
            if (p2.getData().getMaterialType().equals(MaterialType.PRODUCTION)) {
                p1.product = MaterialMasterWithTypeRecord.create(
                        p2.getId(),
                        p2.getParentId(),
                        p2.getData().getMaterialType(),
                        p2.getData().getNameDescInfo(),
                        p2.getData().getMaterialCharacter()
                );
            }
            if (p2.getData().getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)) {
                p1.spares.add(
                        MaterialMasterWithTypeRecord.create(
                                p2.getId(),
                                p2.getParentId(),
                                p2.getData().getMaterialType(),
                                p2.getData().getNameDescInfo(),
                                p2.getData().getMaterialCharacter()
                        )
                );
            }
            if (p2.getData().getMaterialType().equals(MaterialType.RAW_MATERIAL)) {
                p1.rawMaterials.add(
                        MaterialMasterWithTypeRecord.create(
                                p2.getId(),
                                p2.getParentId(),
                                p2.getData().getMaterialType(),
                                p2.getData().getNameDescInfo(),
                                p2.getData().getMaterialCharacter()
                        )
                );
            }
            return p1;
        };
        final MaterialContainer container = new MaterialContainer();
        root.traverse(fn,root,container);
        log.info("container is {}",container);
    }
}

class MaterialContainer {
    public MaterialMasterWithTypeRecord product;

    public final List<MaterialMasterWithTypeRecord> spares = new ArrayList<>();

    public final List<MaterialMasterWithTypeRecord> rawMaterials = new ArrayList<>();


}

class MaterialMasterWithTypeRecord extends MaterialMasterWithType{

    private MaterialId id;

    private MaterialId parentId;
    @SuppressWarnings("unused")
    private MaterialMasterWithTypeRecord(){
        super();
    }

    private MaterialMasterWithTypeRecord(MaterialId id,MaterialId parentId,
                                         MaterialType materialType,
                                         NameDescInfo nameDescInfo,
                                         MaterialCharacter materialCharacter){
        super(materialType,nameDescInfo,materialCharacter);
        this.id = id;
        this.parentId = parentId;
    }

    public static MaterialMasterWithTypeRecord create(MaterialId id,MaterialId parentId,
                                                      MaterialType materialType,
                                                      NameDescInfo nameDescInfo,
                                                      MaterialCharacter materialCharacter){
        return new MaterialMasterWithTypeRecord(id,parentId,materialType,nameDescInfo,materialCharacter);
    }

    public MaterialId getId() {
        return id;
    }

    public MaterialId getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialMasterWithTypeRecord)) return false;
        if (!super.equals(o)) return false;
        MaterialMasterWithTypeRecord that = (MaterialMasterWithTypeRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(parentId, that.parentId) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, parentId);
    }

    @Override
    public String toString() {
        return objToString();
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

@Slf4j
class MaterialHierarchyWithType extends BaseHierarchy<MaterialId, MaterialMasterWithType, MaterialHierarchyWithType>{

    public MaterialHierarchyWithType(MaterialId id, MaterialId parentId, MaterialMasterWithType data) {
        super(id, parentId, data);
    }

    @Override
    void canAddedToHierarchy(MaterialHierarchyWithType node) {
        if (this.getData().getMaterialType().equals(MaterialType.PRODUCTION)
                && node.getData().getMaterialType().equals(MaterialType.PRODUCTION)) {
            throw new RuntimeException("产品不能添加产品作为子件");
        }
        if (this.getData().getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)
                && node.getData().getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)) {
            throw new RuntimeException("在制品不能添加产品作为子件");
        }
        if (this.getData().getMaterialType().equals(MaterialType.RAW_MATERIAL)) {
            throw new RuntimeException("原材料不能添加其他类型作为子件");
        }
    }

    @Override
    void canRemovedToHierarchy(MaterialHierarchyWithType node) {

    }
}

class MaterialMasterWithType extends MaterialMaster {
    private MaterialType materialType;

    @SuppressWarnings("unused")
    protected MaterialMasterWithType(){}

    protected MaterialMasterWithType(MaterialType materialType,
                                   NameDescInfo nameDescInfo,
                                   MaterialCharacter materialCharacter){
        super(nameDescInfo,materialCharacter);
        this.materialType = materialType;
    }

    public static MaterialMasterWithType create(MaterialType materialType,
                                                NameDescInfo nameDescInfo,
                                                MaterialCharacter materialCharacter){
        return new MaterialMasterWithType(materialType,nameDescInfo,materialCharacter);
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialMasterWithType)) return false;
        if (!super.equals(o)) return false;
        MaterialMasterWithType that = (MaterialMasterWithType) o;
        return materialType == that.materialType && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), materialType);
    }

    @Override
    public String toString() {
        return objToString();
    }
}
