package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.material.MaterialMaster;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "material")
public class Material extends AbstractAggregateRoot<MaterialId> {
    @Embedded
    private ChangeableInfo changeableInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "master_name", nullable = false)),
    })
    private MaterialMaster materialMaster;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String json;


    @SuppressWarnings("unused")
    private Material(){
    }

    public Material(ChangeableInfo changeableInfo, MaterialMaster materialMaster){
        super(DomainObjectId.randomId(MaterialId.class));
        this.changeableInfo = changeableInfo;
        this.materialMaster = materialMaster;
    }

    public Material changeName(String name){
        this.changeableInfo = changeableInfo.changeName(name);
        return this;
    }

    public Material changeJson(String json){
        this.json = json;
        return this;
    }


    public String name(){
        return changeableInfo.getName();
    }


}
