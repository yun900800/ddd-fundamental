package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.value.MaterialId;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Embeddable
@MappedSuperclass
public class MaterialBatch implements IBatch, IBatchNoGenerateStrategy{

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
    private IBatchNoGenerateStrategy strategy;

    @Enumerated(EnumType.STRING)
    private BatchType batchType;

    @SuppressWarnings("unused")
    protected MaterialBatch(){}

    public MaterialBatch(MaterialId materialId,int batchNumber,
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

    public MaterialBatch changeStrategy(IBatchNoGenerateStrategy strategy) {
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
        if (!StringUtils.hasLength(batchNo) && null!= strategy ){
            batchNo = strategy.generate(this);
        }
        String generateBatchNo = generate(this);
        if (StringUtils.hasLength(generateBatchNo)){
            return generateBatchNo;
        }
        return batchNo;
    }

    @Override
    public Map<String, String> commonProps() {
        if (null != commonProps) {
            return new HashMap<>(commonProps);
        }
        return null;
    }

    @Override
    public Map<String, String> specialProps() {
        if (null != specialProps) {
            return new HashMap<>(specialProps);
        }
        return null;
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
        return defaultBatchNo(materialId,batchNumber,batchType());
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
