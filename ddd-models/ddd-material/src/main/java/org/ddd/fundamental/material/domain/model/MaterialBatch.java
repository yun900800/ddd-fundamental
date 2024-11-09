package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.domain.value.BatchId;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "m_material_batch")
public class MaterialBatch extends AbstractAggregateRoot<BatchId> {



    private MaterialBatchValue materialBatch;

    @SuppressWarnings("unused")
    private MaterialBatch(){}

    public MaterialBatch(MaterialBatchValue materialBatch){
        this.materialBatch = materialBatch;
    }



    @Override
    public long created() {
        return 0;
    }
}
