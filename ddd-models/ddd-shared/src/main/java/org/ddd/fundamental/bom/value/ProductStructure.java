package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.springframework.util.CollectionUtils;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.*;

@MappedSuperclass
@Embeddable
public class ProductStructure<T> implements ValueObject {

    private MaterialId id;

    private T node;

    private MaterialType materialType;

    private Set<ProductStructure<T>> children;

    @SuppressWarnings("unused")
    private ProductStructure(){}

    public ProductStructure(MaterialId id, T node, MaterialType materialType){
        this.id = id;
        this.node = node;
        this.materialType = materialType;
        this.children = new HashSet<>();
    }

    public MaterialId getId() {
        return id;
    }

    public T getNode() {
        return node;
    }

    public Set<ProductStructure> getChildren() {
        return new HashSet<>(children);
    }

    public List<MaterialIdNode> toMaterialIdList() {
        List<MaterialIdNode> materialIdNodes = new ArrayList<>();
        MaterialId productId = this.id;
        traverse(this, null,materialIdNodes,productId);
        return materialIdNodes;
    }

    /**
     * 根据产品bom的结构生成产品和配件，原材料相关数据
     * @return
     */
    public ProductStructureList toProductStructureList(){
        MaterialId productId = this.id;
        List<MaterialId> spares = new ArrayList<>();
        List<MaterialId> rawMaterials = new ArrayList<>();
        traverse(this,spares,rawMaterials);
        return ProductStructureList.create(productId,spares,rawMaterials);
    }

    public ProductStructureNodeList toProductStructureNodeList(){
        MaterialId productId = this.id;
        List<MaterialIdNode> spares = new ArrayList<>();
        List<MaterialIdNode> rawMaterials = new ArrayList<>();
        traverse(this,null,spares,rawMaterials, productId);
        return ProductStructureNodeList.create(
                MaterialIdNode.create(productId,null,productId),
                spares,rawMaterials);
    }

    public ProductStructure<T> searchById(MaterialId id){
        List<ProductStructure<T>> result = new ArrayList<>();
        traverse(this,id, result);
        return CollectionUtils.isEmpty(result) ? null :result.get(0);
    }

    private void traverse(ProductStructure<T> root,MaterialId id,List<ProductStructure<T>> result){
        if (root.id.equals(id)){
            result.add(root);
            return;
        }
        for (ProductStructure<T> structure: root.children) {
            traverse(structure, id,result);
        }
        return;
    }

    private void traverse(ProductStructure<T> structure, MaterialId parentId,
                          List<MaterialIdNode> spares, List<MaterialIdNode> rawMaterials,
                          MaterialId productId) {
        MaterialId currentId = structure.getId();
        if (structure.materialType.equals(MaterialType.WORKING_IN_PROGRESS)) {
            spares.add(MaterialIdNode.create(currentId,parentId,productId));
        }
        if (structure.materialType.equals(MaterialType.RAW_MATERIAL)){
            rawMaterials.add(MaterialIdNode.create(currentId,parentId,productId));
        }
        if (CollectionUtils.isEmpty(structure.children)){
            return;
        }
        for (ProductStructure<T> temp: structure.getChildren()) {
            traverse(temp,currentId,spares,rawMaterials,productId);
        }
    }

    private void traverse(ProductStructure<T> structure,
                          List<MaterialId> spares, List<MaterialId> rawMaterials) {
        if (structure.materialType.equals(MaterialType.WORKING_IN_PROGRESS)) {
            spares.add(structure.getId());
        }
        if (structure.materialType.equals(MaterialType.RAW_MATERIAL)){
            rawMaterials.add(structure.getId());
        }
        if (CollectionUtils.isEmpty(structure.children)){
            return;
        }
        for (ProductStructure<T> temp: structure.getChildren()) {
            traverse(temp,spares,rawMaterials);
        }
    }



    private void traverse(ProductStructure<T> structure, MaterialId parentId,
                                          List<MaterialIdNode> materialIdNodes,
                          MaterialId productId){
        MaterialId currentId = structure.getId();
        materialIdNodes.add(MaterialIdNode.create(currentId,parentId,productId));
        if (CollectionUtils.isEmpty(structure.children)){
            return;
        }
        for (ProductStructure<T> temp: structure.getChildren()) {
            traverse(temp,currentId,materialIdNodes,productId);
        }
    }

    private void canAddedToStructure(ProductStructure structure){
        if (this.materialType.equals(MaterialType.PRODUCTION)
                && structure.materialType.equals(MaterialType.PRODUCTION)) {
            throw new RuntimeException("产品不能添加产品作为子件");
        }
        if (this.materialType.equals(MaterialType.WORKING_IN_PROGRESS)
                && (structure.materialType.equals(MaterialType.PRODUCTION))) {
            throw new RuntimeException("在制品不能添加产品作为子件");
        }
        if (this.materialType.equals(MaterialType.RAW_MATERIAL)) {
            throw new RuntimeException("原材料不能添加其他类型作为子件");
        }
    }


    public ProductStructure addStructure(ProductStructure structure) {
        canAddedToStructure(structure);
        this.children.add(structure);
        return this;
    }

    public ProductStructure removeStructure(ProductStructure structure) {
        this.children.remove(structure);
        return this;
    }

    public ProductStructure clearStructure(){
        this.children.clear();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStructure)) return false;
        ProductStructure that = (ProductStructure) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductStructure{" +
                "id=" + id +
                ", node=" + node +
                ", materialType=" + materialType +
                ", children=" + children +
                '}';
    }
}

