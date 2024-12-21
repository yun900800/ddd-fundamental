package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class EquipmentRPAccountValue implements ValueObject, Cloneable {

    private WorkOrderId workOrderId;

    private WorkProcessId workProcessId;

    private DateRangeValue dateRangeValue;

    @SuppressWarnings("unused")
    private EquipmentRPAccountValue(){}

    private EquipmentRPAccountValue(WorkOrderId workOrderId,
                                    WorkProcessId workProcessId,
                                    DateRangeValue dateRangeValue){
        this.workOrderId = workOrderId;
        this.workProcessId = workProcessId;
        this.dateRangeValue = dateRangeValue;
    }

    public static EquipmentRPAccountValue create(WorkOrderId workOrderId,
                                                 WorkProcessId workProcessId,
                                                 DateRangeValue dateRangeValue){
        return new EquipmentRPAccountValue(workOrderId,workProcessId,dateRangeValue);
    }

    @Override
    public String toString() {
        return "EquipmentRPAccountValue{" +
                "workOrderId=" + workOrderId +
                ", workProcessId=" + workProcessId +
                ", dateRangeValue=" + dateRangeValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentRPAccountValue)) return false;
        EquipmentRPAccountValue that = (EquipmentRPAccountValue) o;
        return Objects.equals(workOrderId, that.workOrderId) && Objects.equals(workProcessId, that.workProcessId) && Objects.equals(dateRangeValue, that.dateRangeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workOrderId, workProcessId, dateRangeValue);
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }

    public WorkProcessId getWorkProcessId() {
        return workProcessId;
    }

    public DateRangeValue getDateRangeValue() {
        return dateRangeValue;
    }

    @Override
    public EquipmentRPAccountValue clone() {
        try {
            EquipmentRPAccountValue clone = (EquipmentRPAccountValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
