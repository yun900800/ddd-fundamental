package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.PropsContainer;
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
    /**
     * 物料必选属性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "m_required_props")
    private Map<String, String> materialRequiredProps = new HashMap<>();

    /**
     * 物料可选属性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "m_optional_props")
    private Map<String, String> materialOptionalProps = new HashMap<>();

    /**
     * 物料必选特性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "m_required_characteristics")
    private Map<String,String> materialRequiredCharacteristics = new HashMap<>();

    //这里的容易中的json字段不支持重载,为了减少实体的负担,可以将这四个map属性放置到一个容器中
//    @Embedded
//    private PropsContainer materialProps;

    /**
     * 物料可选特性
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "m_optional_characteristics")
    private Map<String,String> materialOptionalCharacteristics = new HashMap<>();


    @SuppressWarnings("unused")
    private Material(){
    }

    public Material(ChangeableInfo changeableInfo, MaterialMaster materialMaster,
                    PropsContainer propsContainer,
                    PropsContainer characterContainer){
        super(DomainObjectId.randomId(MaterialId.class));
        this.changeableInfo = changeableInfo;
        this.materialMaster = materialMaster;
        if (null != propsContainer){
            this.materialRequiredProps = propsContainer.getRequiredMap();
            this.materialOptionalProps = propsContainer.getOptionalMap();
        }
        if (null != characterContainer) {
            this.materialRequiredCharacteristics = characterContainer.getRequiredMap();
            this.materialOptionalCharacteristics = characterContainer.getOptionalMap();
        }

    }

    public Material(ChangeableInfo changeableInfo, MaterialMaster materialMaster){
        this(changeableInfo,materialMaster, null, null);
    }

    public Material changeName(String name){
        this.changeableInfo = changeableInfo.changeName(name);
        return this;
    }

    public Material changeJson(String json){
        this.json = json;
        return this;
    }


    public Material addOptionalProps(String key, String value) {
        this.materialOptionalProps.put(key,value);
        return this;
    }

    public Material addOptionalCharacter(String key, String value){
        this.materialOptionalCharacteristics.put(key,value);
        return this;
    }

    public Map<String, String> getMaterialRequiredProps() {
        return new HashMap<>(materialRequiredProps);
    }

    public Material resetMaterialJson(){
        this.json = null;
        return this;
    }

    public Material resetRequiredProps(){
        this.materialRequiredProps = null;
        return this;
    }

    public Material resetOptionalProps(){
        this.materialOptionalProps = null;
        return this;
    }

    public Material resetRequiredCharacter(){
        this.materialRequiredCharacteristics = null;
        return this;
    }

    public Material resetOptionalCharacter(){
        this.materialOptionalCharacteristics = null;
        return this;
    }

    public Map<String, String> getMaterialOptionalProps() {
        return new HashMap<>(materialOptionalProps);
    }

    public Map<String, String> getMaterialRequiredCharacteristics() {
        return new HashMap<>(materialRequiredCharacteristics);
    }

    public Map<String, String> getMaterialOptionalCharacteristics() {
        return new HashMap<>(materialOptionalCharacteristics);
    }

    public String name(){
        return changeableInfo.getName();
    }

    @Override
    public String toString() {
        return "Material{" +
                "changeableInfo=" + changeableInfo +
                ", materialMaster=" + materialMaster +
                ", json='" + json + '\'' +
                ", materialRequiredProps=" + materialRequiredProps +
                ", materialOptionalProps=" + materialOptionalProps +
                ", materialRequiredCharacteristics=" + materialRequiredCharacteristics +
                ", materialOptionalCharacteristics=" + materialOptionalCharacteristics +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
