package org.ddd.fundamental.event.workprocess;

import org.ddd.fundamental.core.DomainEvent;
import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;

import java.time.Instant;

public class WorkProcessTimeEvent extends BaseDomainEvent<WorkProcessId> implements DomainEvent {

    private WorkOrderId workOrderId;

    private Instant start;

    private Instant end;

    private String reason;

    private EquipmentId equipmentId;

    private ProductResourceType resourceType;

    private Instant occurredOn;

    protected WorkProcessTimeEvent(){
        super(DomainEventType.WORK_PROCESS,null);
    }

    protected WorkProcessTimeEvent(DomainEventType type, WorkProcessId data) {
        super(type, data);
    }

    private WorkProcessTimeEvent(DomainEventType type, WorkProcessId data,
                                 WorkOrderId workOrderId, Instant start, Instant end,
                                 String reason, EquipmentId id, ProductResourceType resourceType){
        super(type, data);
        this.workOrderId = workOrderId;
        this.start = start;
        this.end = end;
        this.reason = reason;
        this.equipmentId = id;
        this.resourceType = resourceType;
        this.occurredOn = Instant.now();
    }

    public static WorkProcessTimeEvent create(DomainEventType type, WorkProcessId data,
                                              WorkOrderId workOrderId, Instant start, Instant end,
                                              String reason, EquipmentId id, ProductResourceType resourceType){
        return new WorkProcessTimeEvent(type, data, workOrderId, start, end,reason,id,resourceType);
    }


    @Override
    protected Class<WorkProcessId> clazzT() {
        return WorkProcessId.class;
    }

    @Override
    protected Class<?> clazz() {
        return WorkProcessTimeEvent.class;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public String getReason() {
        return reason;
    }

    public EquipmentId getEquipmentId() {
        return equipmentId;
    }

    public ProductResourceType getResourceType() {
        return resourceType;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "WorkProcessTimeEvent{" +
                "workOrderId=" + workOrderId +
                ", start=" + start +
                ", end=" + end +
                ", reason='" + reason + '\'' +
                ", equipmentId=" + equipmentId +
                ", resourceType=" + resourceType +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
