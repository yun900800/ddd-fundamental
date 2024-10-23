package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.material.MaterialMaster;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "material")
public class Material extends AbstractAggregateRoot<MaterialId> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "material_name", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "material_desc", nullable = false))
    })
    private ChangeableInfo changeableInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "master_name", nullable = false)),
            @AttributeOverride(name = "code", column = @Column(name = "master_code", nullable = false)),
    })
    private MaterialMaster materialMaster;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "material_json")
    private String json;

    // MYSQL 执行json查询 SELECT * FROM material s WHERE s.material_props->'$.usage'='生产电路板' AND s.material_json->'$.name'='kafka';
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "material_props")
    private Map<String, String> materialProps = new HashMap<>();


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

    public Material putMaterialProps(String key,String value){
        this.materialProps.put(key,value);
        return this;
    }

    public Map<String, String> getMaterialProps() {
        return new HashMap<>(materialProps);
    }

    public Material removeMaterialProps(String key){
        this.materialProps.remove(key);
        return this;
    }

    public Material resetMaterialJson(){
        this.json = null;
        return this;
    }

    public Material resetMaterialProps(){
        this.materialProps = null;
        return this;
    }


    public String name(){
        return changeableInfo.getName();
    }


}
