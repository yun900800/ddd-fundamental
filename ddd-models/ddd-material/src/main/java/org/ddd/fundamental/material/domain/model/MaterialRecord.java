package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.value.MaterialRecordId;
import org.ddd.fundamental.material.MaterialRecordValue;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "material_record")
public class MaterialRecord extends AbstractAggregateRoot<MaterialRecordId> {

    @Embedded
    private MaterialRecordValue record;

    @SuppressWarnings("unused")
    private MaterialRecord(){}

    public MaterialRecord(MaterialRecordValue record){
        this.record = record;
    }

    public MaterialRecordValue getRecord() {
        return record;
    }

    @Override
    public long created() {
        return 0;
    }
}
