package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.value.MaterialId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Embeddable
@MappedSuperclass
public class MaterialBatchValue implements IBatch, IBatchNoGenerateStrategy{

    /**
     * 批次对应的物料id
     */
    private MaterialId materialId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 通用属性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "common_props")
    private Map<String,String> commonProps = new HashMap<>();

    /**
     * 特殊属性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "special_props")
    private Map<String,String> specialProps = new HashMap<>();;

    private int batchNumber;

    private boolean canSplit = false;

    private boolean canMerge = false;

    //生成批次号的接口
    @Transient
    private IBatchNoGenerateStrategy strategy = this;

    @Enumerated(EnumType.STRING)
    private BatchType batchType;

    @SuppressWarnings("unused")
    protected MaterialBatchValue(){}

    public MaterialBatchValue(MaterialId materialId, int batchNumber,
                              BatchType batchType){
        this.materialId = materialId;
        this.batchNumber = batchNumber;
        this.batchType = batchType;
        erpBatchCanSplit();
    }

    private void erpBatchCanSplit(){
        if (this.batchType.equals(BatchType.ERP_BATCH)) {
            this.canSplit = true;
        }
    }

    public MaterialBatchValue changeStrategy(IBatchNoGenerateStrategy strategy) {
        this.strategy = strategy;
        this.batchNo = strategy.generate(this);
        return this;
    }


    @Override
    public MaterialId materialId() {
        return materialId;
    }

    @Override
    public String batchNo() {
        batchNo = strategy.generate(this);
        return batchNo;
    }

    /**
     * 添加通用属性
     * @param key
     * @param value
     * @return
     */
    public MaterialBatchValue addCommonProps(String key,String value){
        this.commonProps.put(key,value);
        return this;
    }

    /**
     * 移除通用属性
     * @param key
     * @return
     */
    public MaterialBatchValue removeCommonProps(String key){
        this.commonProps.remove(key);
        return this;
    }

    public MaterialBatchValue clearCommonProps(){
        this.commonProps.clear();
        return this;
    }

    @Override
    public Map<String, String> commonProps() {
        if (null != commonProps) {
            return new HashMap<>(commonProps);
        }
        return null;
    }

    /**
     * 添加特殊属性
     * @param key
     * @param value
     * @return
     */
    public MaterialBatchValue addSpecialProps(String key,String value){
        this.specialProps.put(key,value);
        return this;
    }

    /**
     * 移除特殊属性
     * @param key
     * @return
     */
    public MaterialBatchValue removeSpecialProps(String key){
        this.specialProps.remove(key);
        return this;
    }

    public MaterialBatchValue clearSpecialProps(){
        this.specialProps.clear();
        return this;
    }

    @Override
    public Map<String, String> specialProps() {
        if (null != specialProps) {
            return new HashMap<>(specialProps);
        }
        return null;
    }

    public MaterialBatchValue changeBatchType(BatchType batchType){
        this.batchType = batchType;
        return this;
    }


    @Override
    public int batchNumber() {
        return batchNumber;
    }

    @Override
    public boolean canSplit() {
        return canSplit;
    }

    @Override
    public boolean canMerge() {
        return canMerge;
    }

    @Override
    public BatchType batchType() {
        return batchType;
    }

    @Override
    public String generate(IBatch batch) {
        //默认的批次号,可以通过不同的策略进行修改
        return defaultBatchNo(materialId(),batchNumber(),batchType());
    }

    private static String defaultBatchNo(MaterialId id, int batchNumber,BatchType batchType){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pre = simpleDateFormat.format(date);
        String materialId = id.toUUID();
        return pre + "_" + materialId +"_" +batchType.getValue()+ "_"  + batchNumber;
    }

    @Override
    public String toString() {
        return "MaterialBatch{" +
                "materialId=" + materialId +
                ", batchNo='" + batchNo + '\'' +
                ", commonProps=" + commonProps +
                ", specialProps=" + specialProps +
                ", batchNumber=" + batchNumber +
                ", canSplit=" + canSplit +
                ", canMerge=" + canMerge +
                ", strategy=" + strategy +
                ", batchType=" + batchType +
                '}';
    }
}
