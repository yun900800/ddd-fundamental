package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.infra.hibernate.WorkOrderIdType;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "w_work_order")
public class WorkOrder extends AbstractAggregateRoot<WorkOrderId> {

    private WorkOrderValue order;

    @SuppressWarnings("unused")
    private WorkOrder(){}

    public WorkOrder(WorkOrderValue order){
        this.order = order;
    }

    public WorkOrderValue getOrder() {
        return order.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
