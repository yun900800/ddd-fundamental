package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.hibernate.annotations.Type;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

@Embeddable
@MappedSuperclass
public class EquipmentResourceValue extends ProductResource<EquipmentId> {

    /**
     * 设备资源或者工装的计划时间段
     */
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(
//            name="equipment_date_range",
//            joinColumns=@JoinColumn(name="resource_id")
//    )
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_date_range")
    private List<EquipmentPlanRange> planRanges = new ArrayList<>();

    /**
     * 设备能够处理的输入物料(可以是原材料或者在制品)
     */
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_inputs")
    private Set<MaterialId> inputs = new HashSet<>();

    /**
     * 设备能够处理的输入物料(可以是在制品或者产品)
     */
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_outputs")
    private Set<MaterialId> outputs = new HashSet<>();

    /**
     * 设备资源或者工装的使用时间段
     */
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_use_range")
    private EquipmentPlanRange useRange;

    /**
     * 设备是否正在使用
     */
    private boolean used;

    private EquipmentResourceValue() {
        super(null, null,null);
    }

    private EquipmentResourceValue(EquipmentId id, ProductResourceType resourceType,
                                   ChangeableInfo info, EquipmentPlanRange useRange){
        super(id,resourceType,info);
        this.useRange = useRange;
        if (null == useRange){
            this.used =  false;
        }
        this.planRanges = new ArrayList<>();
    }

    public static EquipmentResourceValue create(EquipmentId id, ProductResourceType resourceType,
                                                ChangeableInfo info, EquipmentPlanRange useRange){
        return new EquipmentResourceValue(id,resourceType,info,useRange);
    }
    public static EquipmentResourceValue create(EquipmentId id, ProductResourceType resourceType,
                                                ChangeableInfo info){
        return new EquipmentResourceValue(id,resourceType,info,null);
    }

    public EquipmentResourceValue addMaterialInput(MaterialId input){
        this.inputs.add(input);
        return this;
    }

    public EquipmentResourceValue removeMaterialInput(MaterialId input){
        this.inputs.remove(input);
        return this;
    }

    public EquipmentResourceValue addMaterialOutput(MaterialId output){
        this.outputs.add(output);
        return this;
    }

    public EquipmentResourceValue removeMaterialOutput(MaterialId output){
        this.outputs.remove(output);
        return this;
    }

    public EquipmentResourceValue recordUseRange(EquipmentPlanRange value){
        this.useRange = value;
        this.used = true;
        return this;
    }

    public EquipmentResourceValue finishUseRange(){
        this.useRange = null;
        this.used = false;
        return this;
    }

    public EquipmentResourceValue addRange(EquipmentPlanRange value){
        if (CollectionUtils.isEmpty(planRanges)) {
            planRanges.add(value);
            return this;
        }
        validate(value);
        planRanges.add(value);
        return this;
    }

    private void validate(EquipmentPlanRange value){
        int size = planRanges.size();
        EquipmentPlanRange lastValue = planRanges.get(size-1);
        if (!value.getDateRangeValue().isAfterRange(lastValue.getDateRangeValue())) {
            throw new RuntimeException("添加的DateRange必须在最后DateRange之后");
        }
    }

    public EquipmentResourceValue removeRange(EquipmentPlanRange value){
        this.planRanges.remove(value);
        return this;
    }

    public EquipmentResourceValue clearRanges(){
        this.planRanges.clear();
        return this;
    }


    public List<EquipmentPlanRange> getPlanRanges() {
        return new ArrayList<>(planRanges);
    }

    public EquipmentPlanRange getUseRange() {
        if (null != useRange) {
            return useRange.clone();
        }
        return null;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentResourceValue)) return false;
        if (!super.equals(o)) return false;
        EquipmentResourceValue resource = (EquipmentResourceValue) o;
        return used == resource.used && Objects.equals(planRanges, resource.planRanges) && Objects.equals(useRange, resource.useRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), planRanges);
    }

    @Override
    public String toString() {
        return "EquipmentResource{" +
                "planRanges=" + planRanges +
                ", useRange=" + useRange +
                ", used=" + used +
                '}';
    }
}
