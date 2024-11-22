package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.hibernate.annotations.Type;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class EquipmentResourceValue extends ProductResource<EquipmentId> {

    /**
     * 设备资源或者工装的计划时间段
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="equipment_date_range",
            joinColumns=@JoinColumn(name="resource_id")
    )
    private List<DateRangeValue> planRanges = new ArrayList<>();

    /**
     * 设备资源或者工装的使用时间段
     */
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_use_range")
    private DateRangeValue useRange;

    /**
     * 设备是否正在使用
     */
    private boolean used;

    private EquipmentResourceValue() {
        super(null, null,null);
    }

    private EquipmentResourceValue(EquipmentId id, ProductResourceType resourceType,
                                   ChangeableInfo info, DateRangeValue useRange){
        super(id,resourceType,info);
        this.useRange = useRange;
        if (null == useRange){
            this.used =  false;
        }
        this.planRanges = new ArrayList<>();
    }

    public static EquipmentResourceValue create(EquipmentId id, ProductResourceType resourceType,
                                                ChangeableInfo info, DateRangeValue useRange){
        return new EquipmentResourceValue(id,resourceType,info,useRange);
    }
    public static EquipmentResourceValue create(EquipmentId id, ProductResourceType resourceType,
                                                ChangeableInfo info){
        return new EquipmentResourceValue(id,resourceType,info,null);
    }

    public EquipmentResourceValue recordUseRange(DateRangeValue value){
        this.useRange = value;
        this.used = true;
        return this;
    }

    public EquipmentResourceValue finishUseRange(){
        this.useRange = null;
        this.used = false;
        return this;
    }

    public EquipmentResourceValue addRange(DateRangeValue value){
        if (CollectionUtils.isEmpty(planRanges)) {
            planRanges.add(value);
            return this;
        }
        validate(value);
        planRanges.add(value);
        return this;
    }

    private void validate(DateRangeValue value){
        int size = planRanges.size();
        DateRangeValue lastValue = planRanges.get(size-1);
        if (!value.isAfterRange(lastValue)) {
            throw new RuntimeException("添加的DateRange必须在最后DateRange之后");
        }
    }

    public EquipmentResourceValue removeRange(DateRangeValue value){
        this.planRanges.remove(value);
        return this;
    }

    public EquipmentResourceValue clearRanges(){
        this.planRanges.clear();
        return this;
    }


    public List<DateRangeValue> getPlanRanges() {
        return new ArrayList<>(planRanges);
    }

    public DateRangeValue getUseRange() {
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
