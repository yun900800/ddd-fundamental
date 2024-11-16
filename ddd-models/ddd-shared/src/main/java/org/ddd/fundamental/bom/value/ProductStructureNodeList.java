package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ProductStructureNodeList implements ValueObject,Cloneable{

    /**
     * 产品节点
     */
    private MaterialIdNode productNode;

    /**
     * 零部件节点
     */
    private List<MaterialIdNode> sparePartNodes;

    /**
     * 原材料ids
     */
    private List<MaterialIdNode> rawMaterialNodes;

    @SuppressWarnings("unused")
    private ProductStructureNodeList(){}

    private ProductStructureNodeList(MaterialIdNode productNode,
                                     List<MaterialIdNode> sparePartNodes,
                                     List<MaterialIdNode> rawMaterialNodes){
        this.productNode = productNode;
        this.sparePartNodes = sparePartNodes;
        this.rawMaterialNodes = rawMaterialNodes;
    }

    public static ProductStructureNodeList create(MaterialIdNode productNode,
                                                  List<MaterialIdNode> sparePartNodes,
                                                  List<MaterialIdNode> rawMaterialNodes){
        return new ProductStructureNodeList(productNode,sparePartNodes,rawMaterialNodes);
    }

    public MaterialIdNode getProductNode() {
        return productNode;
    }

    public List<MaterialIdNode> getSparePartNodes() {
        return sparePartNodes;
    }

    public List<MaterialIdNode> getRawMaterialNodes() {
        return rawMaterialNodes;
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
