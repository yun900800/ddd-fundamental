package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.MaterialRecordId;
import org.ddd.fundamental.material.MaterialRecordValueObject;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "material_record")
public class MaterialRecord extends AbstractAggregateRoot<MaterialRecordId> {

    @Embedded
    private MaterialRecordValueObject record;

    @SuppressWarnings("unused")
    private MaterialRecord(){}

    public MaterialRecord(MaterialRecordValueObject record){
        this.record = record;
    }

    public MaterialRecordValueObject getRecord() {
        return record;
    }
}
