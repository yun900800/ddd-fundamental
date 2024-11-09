package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.domain.value.BatchId;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;

import javax.persistence.*;

@Entity
@Table(name = "m_material_batch")
public class MaterialBatch extends AbstractAggregateRoot<BatchId> {


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "material_batch_name", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "material_batch_desc", nullable = false))
    })
    private ChangeableInfo batchInfo;

    @Embedded
    private MaterialBatchValue materialBatch;

    @SuppressWarnings("unused")
    private MaterialBatch(){}

    public MaterialBatch(MaterialBatchValue materialBatch, ChangeableInfo batchInfo){
        super(BatchId.randomId(BatchId.class));
        this.materialBatch = materialBatch;
        this.batchInfo = batchInfo;
    }

    public static MaterialBatch create(MaterialBatchValue materialBatch, ChangeableInfo batchInfo){
        return new MaterialBatch(materialBatch, batchInfo);
    }

    public ChangeableInfo getBatchInfo() {
        return batchInfo.clone();
    }

    public MaterialBatchValue getMaterialBatch() {
        return materialBatch;
    }

    @Override
    public long created() {
        return 0;
    }

    @Override
    public String toString() {
        return "MaterialBatch{" +
                "batchInfo=" + batchInfo +
                ", materialBatch=" + materialBatch +
                '}';
    }
}
