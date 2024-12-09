package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;

import javax.persistence.*;

@Entity
@Table(name = "work_process_record")
public class WorkProcessRecord extends AbstractAggregateRoot<WorkProcessId> {

    /**
     * 工序基本信息
     */
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "work_process_name", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "work_process_desc" ,nullable = false)),
            @AttributeOverride(name = "isUse", column = @Column(name = "work_process_is_use" ,nullable = false))
    })
    @Embedded
    private ChangeableInfo processInfo;

    /**
     * 工序资源,时间,
     */
    @Embedded
    private WorkProcessValue workProcessValue;


    private WorkOrderId workOrderId;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL,
            optional = false,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private WorkProcessTimeEntity workProcessTime;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "quantity_id")
    private WorkProcessQuantityEntity quantity;


    @SuppressWarnings("unused")
    private WorkProcessRecord(){}

    private WorkProcessRecord(ChangeableInfo processInfo,
                              WorkProcessValue workProcessValue,
                              WorkOrderId workOrderId,
                              WorkOrderValue workOrderValue){
        super(WorkProcessId.randomId(WorkProcessId.class));
        this.processInfo = processInfo;
        this.workProcessValue = workProcessValue;
        this.workOrderId = workOrderId;
        this.registerEvent(WorkProcessRecordCreated.create(DomainEventType.WORK_PROCESS,
                workProcessValue,workOrderId,workProcessValue.getProductResources(),this.id(),workOrderValue));
    }

    public WorkProcessQuantityEntity getQuantity() {
        return quantity;
    }

    public void setQuantity(WorkProcessQuantityEntity quantity) {
        this.quantity = quantity;
    }

    public WorkProcessTimeEntity getWorkProcessTime() {
        return workProcessTime;
    }

    public void setWorkProcessTime(WorkProcessTimeEntity workProcessTime) {
        if (workProcessTime == null) {
            if (this.workProcessTime != null) {
                this.workProcessTime.setRecord(null);
            }
        }
        else {
            workProcessTime.setRecord(this);
        }
        this.workProcessTime = workProcessTime;
    }

    public static WorkProcessRecord create(ChangeableInfo processInfo,
                                           WorkProcessValue workProcessValue,
                                           WorkOrderId workOrderId,
                                           WorkOrderValue workOrderValue){
        return new WorkProcessRecord(processInfo, workProcessValue,workOrderId,workOrderValue);
    }


    public ChangeableInfo getProcessInfo() {
        return processInfo.clone();
    }

    public WorkProcessValue getWorkProcessValue() {
        return workProcessValue.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessRecord{" +
                "processInfo=" + processInfo +
                ", workProcessValue=" + workProcessValue +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
