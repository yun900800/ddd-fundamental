package org.ddd.fundamental.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.BomId;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.*;

@MappedSuperclass
@Embeddable
@Slf4j
public class MasterProductScheduleValue implements ValueObject, Cloneable{

    /**
     * 加工产品需要的原料,这个可以用bom来提供
     */
    private BomId bomId;

    /**
     * 已经拥有的原材料或者零件(在制品)
     */
    private Set<MaterialId> existMaterials = new HashSet<>();

    /**
     * 加工产品需要的其他资源
     */
    private ProductResources productResources = new ProductResources(new HashSet<>());

    /**
     * 原料什么时候需要
     */
    private Instant useTime;

    /**
     * 原料需要的数量
     */
    private Map<MaterialId,Double> rawMaterialQty = new HashMap<>();

    /**
     * 生产一批次最晚结束时间
     */
    private Instant finishTime;

    @SuppressWarnings("unused")
    protected MasterProductScheduleValue(){}

    private MasterProductScheduleValue(BomId bomId,
                                       Instant useTime, Instant finishTime
                                       ){
        this.bomId = bomId;
        this.existMaterials = new HashSet<>();
        this.productResources = new ProductResources(new HashSet<>());
        this.useTime = useTime;
        this.finishTime = finishTime;
        this.rawMaterialQty = new HashMap<>();
    }

    public static MasterProductScheduleValue create(BomId bomId,
                                                    Instant useTime, Instant finishTime){
        return new MasterProductScheduleValue(bomId,
                useTime,finishTime);
    }

    /**
     * 配置物料的数量
     * @param rawMaterialId
     * @param qty
     * @return
     */
    public MasterProductScheduleValue configureRawMaterialQty(MaterialId rawMaterialId, double qty){
        this.rawMaterialQty.put(rawMaterialId,qty);
        return this;
    }

    /**
     * 添加产品资源
     * @param productResource
     * @return
     */
    public MasterProductScheduleValue addResources(ProductResource productResource){
        this.productResources.addResource(productResource);
        return this;
    }

    public MasterProductScheduleValue addExistMaterial(MaterialId existId){
        this.existMaterials.add(existId);
        return this;
    }

    public BomId getBomId() {
        return bomId;
    }

    public Set<MaterialId> getExistMaterials() {
        return new HashSet<>(existMaterials);
    }

    public ProductResources getProductResources() {
        return productResources;
    }

    public Instant getUseTime() {
        return useTime;
    }

    public Map<MaterialId, Double> getRawMaterialQty() {
        return new HashMap<>(rawMaterialQty);
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    @Override
    public String toString() {
        return "MasterProductScheduleValue{" +
                "bomId=" + bomId +
                ", existMaterials=" + existMaterials +
                ", productResources=" + productResources +
                ", useTime=" + useTime +
                ", rawMaterialQty=" + rawMaterialQty +
                ", finishTime=" + finishTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterProductScheduleValue)) return false;
        MasterProductScheduleValue that = (MasterProductScheduleValue) o;
        return Objects.equals(bomId, that.bomId) && Objects.equals(existMaterials, that.existMaterials) && Objects.equals(productResources, that.productResources) && Objects.equals(useTime, that.useTime) && Objects.equals(rawMaterialQty, that.rawMaterialQty) && Objects.equals(finishTime, that.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bomId, existMaterials, productResources, useTime, rawMaterialQty, finishTime);
    }

    @Override
    public MasterProductScheduleValue clone() {
        try {
            MasterProductScheduleValue clone = (MasterProductScheduleValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
