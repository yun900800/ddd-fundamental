package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.value.MaterialId;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Embeddable
@MappedSuperclass
public class MaterialBatch implements IBatch{

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


    @Override
    public MaterialId materialId() {
        return materialId;
    }

    @Override
    public String batchNo() {
        if (!StringUtils.hasLength(batchNo)){
            batchNo = strategy.generate(this);
        }
        return batchNo;
    }

    @Override
    public Map<String, String> commonProps() {
        return commonProps;
    }

    @Override
    public Map<String, String> specialProps() {
        return specialProps;
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
}
