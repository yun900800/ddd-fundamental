package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.ddd.fundamental.event.workprocess.WorkProcessTimeEvent;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    protected WorkProcessRecord(){}

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

    private List<ProductResource> getProductResource(ProductResourceType type){
        List<ProductResource> productResourceList = new ArrayList<>();
        ProductResources resources = this.getWorkProcessValue().getProductResources();
        for (ProductResource resource: resources.getResources()) {
            if (resource.getResourceType().equals(type)){
                productResourceList.add(resource);
            }
        }
        return productResourceList;
    }

    /**
     * 直接启动工序准备时间
     * @return
     */
    public WorkProcessRecord directStartProcess(){
        this.workProcessTime = this.workProcessTime.directStartProcess();
        Instant start = this.workProcessTime.getKeyTime().getInitTime();
        Instant end = this.workProcessTime.getKeyTime().getStartTime();
        String desc = "从工单工序创建成功到直接启动花费的时间";
        List<ProductResource> equipmentResources = getProductResource(ProductResourceType.EQUIPMENT);
        for (ProductResource resource: equipmentResources){
            this.registerEvent(WorkProcessTimeEvent.create(DomainEventType.WORK_PROCESS,
                    this.getId(), workOrderId, start,end,desc,
                    (EquipmentId) resource.getId(), ProductResourceType.EQUIPMENT)
            );
        }
        List<ProductResource> ToolingResources = getProductResource(ProductResourceType.TOOLING);
        for (ProductResource resource: ToolingResources){
            this.registerEvent(WorkProcessTimeEvent.create(DomainEventType.WORK_PROCESS,
                    this.getId(), workOrderId, start,end,desc,
                    (EquipmentId) resource.getId(), ProductResourceType.TOOLING)
            );
        }
        return this;
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
