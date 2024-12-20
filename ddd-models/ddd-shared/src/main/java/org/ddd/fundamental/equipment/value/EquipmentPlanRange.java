package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

//@Embeddable
@MappedSuperclass
public class EquipmentPlanRange implements ValueObject, Cloneable {
    private WorkOrderId workOrderId;

    private WorkProcessId workProcessId;

    @Embedded
    private DateRangeValue dateRangeValue;

    @SuppressWarnings("unused")
    public EquipmentPlanRange(){}

    private EquipmentPlanRange(DateRangeValue dateRangeValue,
                               WorkOrderId workOrderId,
                               WorkProcessId workProcessId){
        this.dateRangeValue = dateRangeValue;
        this.workOrderId = workOrderId;
        this.workProcessId = workProcessId;
    }

    public static EquipmentPlanRange create(DateRangeValue dateRangeValue,
                                            WorkOrderId workOrderId,
                                            WorkProcessId workProcessId){
        return new EquipmentPlanRange(dateRangeValue,workOrderId,workProcessId);
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }

    public WorkProcessId getWorkProcessId() {
        return workProcessId;
    }

    public DateRangeValue getDateRangeValue() {
        return dateRangeValue.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentPlanRange)) return false;
        EquipmentPlanRange that = (EquipmentPlanRange) o;
        return Objects.equals(workOrderId, that.workOrderId) && Objects.equals(workProcessId, that.workProcessId) && Objects.equals(dateRangeValue, that.dateRangeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workOrderId, workProcessId, dateRangeValue);
    }

    @Override
    public String toString() {
        return "EquipmentPlanRange{" +
                "workOrderId=" + workOrderId +
                ", workProcessId=" + workProcessId +
                ", dateRangeValue=" + dateRangeValue +
                '}';
    }

    @Override
    public EquipmentPlanRange clone() {
        try {
            EquipmentPlanRange clone = (EquipmentPlanRange) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
