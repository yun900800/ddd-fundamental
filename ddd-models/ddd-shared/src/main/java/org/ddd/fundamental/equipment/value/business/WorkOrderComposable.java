package org.ddd.fundamental.equipment.value.business;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class WorkOrderComposable implements ValueObject {

    @Column(name = "business_work_order_id")
    private WorkOrderId workOrderId;

    @Column(name = "business_work_process_id")
    private WorkProcessId workProcessId;

    @SuppressWarnings("unused")
    protected WorkOrderComposable(){
    }

    private WorkOrderComposable(WorkOrderId workOrderId,
                                WorkProcessId workProcessId){
        this.workOrderId = workOrderId;
        this.workProcessId = workProcessId;
    }

    public static WorkOrderComposable create(WorkOrderId workOrderId,
                                             WorkProcessId workProcessId){
        return new WorkOrderComposable(workOrderId,workProcessId);
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }

    public WorkProcessId getWorkProcessId() {
        return workProcessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkOrderComposable)) return false;
        WorkOrderComposable that = (WorkOrderComposable) o;
        return Objects.equals(workOrderId, that.workOrderId) && Objects.equals(workProcessId, that.workProcessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workOrderId, workProcessId);
    }

    @Override
    public String toString() {
        return "WorkOrderComposable{" +
                "workOrderId=" + workOrderId +
                ", workProcessId=" + workProcessId +
                '}';
    }
}
