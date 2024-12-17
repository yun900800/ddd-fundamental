package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ProductStructureNodeList<T> implements ValueObject,Cloneable{

    /**
     * 产品节点
     */
    private MaterialIdNode<T> productNode;

    /**
     * 零部件节点
     */
    private List<MaterialIdNode<T>> sparePartNodes;

    /**
     * 原材料ids
     */
    private List<MaterialIdNode<T>> rawMaterialNodes;

    @SuppressWarnings("unused")
    private ProductStructureNodeList(){}

    private ProductStructureNodeList(MaterialIdNode<T> productNode,
                                     List<MaterialIdNode<T>> sparePartNodes,
                                     List<MaterialIdNode<T>> rawMaterialNodes){
        this.productNode = productNode;
        this.sparePartNodes = sparePartNodes;
        this.rawMaterialNodes = rawMaterialNodes;
    }

    public static <T> ProductStructureNodeList create(MaterialIdNode<T> productNode,
                                                  List<MaterialIdNode<T>> sparePartNodes,
                                                  List<MaterialIdNode<T>> rawMaterialNodes){
        return new ProductStructureNodeList(productNode,sparePartNodes,rawMaterialNodes);
    }

    public ProductStructure<MaterialIdNode<T>> toProductStructure(){
        ProductStructure<MaterialIdNode<T>> root = new ProductStructure<>(
                productNode.getProductId(), productNode, MaterialType.PRODUCTION
        );
        addChild(root);
        return root;
    }
    private void addChild(ProductStructure<MaterialIdNode<T>> root){
        MaterialId id = root.getId();
        for (MaterialIdNode<T> node: sparePartNodes){
            if (node.getParent().equals(id)) {
                root.addStructure(
                        new ProductStructure<>(
                                node.getCurrent(), node, MaterialType.WORKING_IN_PROGRESS
                        )
                );
            }
        }
        for (MaterialIdNode<T> node: rawMaterialNodes) {
            if (node.getParent().equals(id)) {
                root.addStructure(
                        new ProductStructure<>(
                                node.getCurrent(), node, MaterialType.RAW_MATERIAL
                        )
                );
            }
        }
        for (ProductStructure<MaterialIdNode<T>> node: root.getChildren()) {
            addChild(node);
        }
    }

    public MaterialIdNode<T> getProductNode() {
        return productNode;
    }

    public List<MaterialIdNode<T>> getSparePartNodes() {
        return sparePartNodes;
    }

    public List<MaterialIdNode<T>> getRawMaterialNodes() {
        return rawMaterialNodes;
    }

    public List<MaterialIdNode<T>> mergeToMaterialIdNodeList(){
        List<MaterialIdNode<T>> result = new ArrayList<>();
        result.add(productNode);
        result.addAll(sparePartNodes);
        result.addAll(rawMaterialNodes);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStructureNodeList)) return false;
        ProductStructureNodeList that = (ProductStructureNodeList) o;
        return Objects.equals(productNode, that.productNode) && Objects.equals(sparePartNodes, that.sparePartNodes) && Objects.equals(rawMaterialNodes, that.rawMaterialNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNode, sparePartNodes, rawMaterialNodes);
    }

    @Override
    public String toString() {
        return "ProductStructureNodeList{" +
                "productNode=" + productNode +
                ", sparePartNodes=" + sparePartNodes +
                ", rawMaterialNodes=" + rawMaterialNodes +
                '}';
    }

    @Override
    public ProductStructureNodeList clone() {
        try {
            ProductStructureNodeList clone = (ProductStructureNodeList) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
