package org.ddd.fundamental.material.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.material.value.PropsContainer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个聚合根中的属性太多,根据业务合理的进行分组,然后进行不同的API进行操作是一个好的设计方式
 */
@Entity
@Table(name = "material")
@Slf4j
public class Material extends AbstractAggregateRoot<MaterialId> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "material_name", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "material_desc", nullable = false))
    })
    private ChangeableInfo materialInfo;



    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "master_name", nullable = false)),
            @AttributeOverride(name = "code", column = @Column(name = "master_code", nullable = false)),
    })
    private MaterialMaster materialMaster;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "material_json")
    private String json;

    /**
     * 物料控制属性
     */
    @Embedded
    private ControlProps materialControlProps;

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
                    PropsContainer characterContainer,
                    ControlProps materialControlProps){
        super(DomainObjectId.randomId(MaterialId.class));
        this.materialInfo = changeableInfo;
        this.materialMaster = materialMaster;
        if (null != propsContainer){
            this.materialRequiredProps = propsContainer.getRequiredMap();
            this.materialOptionalProps = propsContainer.getOptionalMap();
        }
        if (null != characterContainer) {
            this.materialRequiredCharacteristics = characterContainer.getRequiredMap();
            this.materialOptionalCharacteristics = characterContainer.getOptionalMap();
        }
        this.materialControlProps = materialControlProps;
        if (MaterialType.PRODUCTION.equals(materialControlProps.getMaterialType())){
            this.registerEvent(ProductEventCreated.create(DomainEventType.MATERIAL,
                    materialMaster, materialControlProps.getMaterialType(), id()));
        }

    }

    public Material(ChangeableInfo changeableInfo, MaterialMaster materialMaster){
        this(changeableInfo,materialMaster, null, null,null);
    }

    public ChangeableInfo getMaterialInfo() {
        return materialInfo.clone();
    }

    public Material changeMaterialControl(ControlProps controlProps) {
        this.materialControlProps = controlProps;
        changeUpdated();
        return this;
    }

    /**
     * 修改主数据
     * @param materialMaster
     * @return
     */
    public Material changeMaterialMaster(MaterialMaster materialMaster){
        this.materialMaster = materialMaster;
        changeUpdated();
        return this;
    }

    public Material changeMaterialInfo(ChangeableInfo info){
        this.materialInfo = info;
        changeUpdated();
        return this;
    }

    public Material changeName(String name){
        this.materialInfo = materialInfo.changeName(name);
        changeUpdated();
        return this;
    }

    public Material changeDesc(String desc){
        this.materialInfo = materialInfo.changeDesc(desc);
        changeUpdated();
        return this;
    }

    public Material changeJson(String json){
        this.json = json;
        return this;
    }


    public Material addOptionalProps(String key, String value) {
        this.materialOptionalProps.put(key,value);
        changeUpdated();
        return this;
    }

    public Material addOptionalCharacter(String key, String value){
        this.materialOptionalCharacteristics.put(key,value);
        changeUpdated();
        return this;
    }

    /**
     * 集合对象在返回的时候需要判空
     * @return
     */
    public Map<String, String> getMaterialRequiredProps() {
        if (null != materialRequiredProps){
            return new HashMap<>(materialRequiredProps);
        }
        return null;
    }

    public Map<String, String> getMaterialOptionalProps() {
        if (null != materialOptionalProps){
            return new HashMap<>(materialOptionalProps);
        }
        return null;
    }

    public Map<String, String> getMaterialRequiredCharacteristics() {
        if (null != materialRequiredCharacteristics){
            return new HashMap<>(materialRequiredCharacteristics);
        }
        return null;
    }

    public Map<String, String> getMaterialOptionalCharacteristics() {
        if (null != materialOptionalCharacteristics){
            return new HashMap<>(materialOptionalCharacteristics);
        }
        return null;
    }

    public MaterialMaster getMaterialMaster() {
        if (null== materialMaster){
            return null;
        }
        return materialMaster.clone();
    }

    public MaterialType getMaterialType(){
        if (null== materialControlProps){
            return MaterialType.PRODUCTION;
        }
        return materialControlProps.getMaterialType();
    }

    public ControlProps getMaterialControlProps() {
        if (null== materialControlProps){
            return null;
        }
        return materialControlProps.clone();
    }

    public String name(){
        return materialInfo.getName();
    }

    @Override
    public String toString() {
        return "Material{" +
                "changeableInfo=" + materialInfo +
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
