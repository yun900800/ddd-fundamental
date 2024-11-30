package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantityId;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "w_work_process_quantity")
public class WorkProcessQuantityEntity extends AbstractEntity<WorkProcessQuantityId> {

    @Embedded
    private WorkProcessQuantity quantity;

    @SuppressWarnings("unused")
    protected WorkProcessQuantityEntity(){}

    private WorkProcessQuantityEntity(WorkProcessQuantity quantity){
        super(WorkProcessQuantityId.randomId(WorkProcessQuantityId.class));
        this.quantity = quantity;
    }

    public static WorkProcessQuantityEntity create(WorkProcessQuantity quantity){
        return new WorkProcessQuantityEntity(quantity);
    }

    /**
     * 修改工序的目标数量
     * @param targetQuantity
     * @return
     */
    public WorkProcessQuantityEntity changeTargetQuantity(int targetQuantity) {
        this.quantity = this.quantity.changeTargetQuantity(targetQuantity);
        return this;
    }

    public WorkProcessQuantityEntity changeUnQualifiedQuantity(int unQualifiedQuantity){
        this.quantity = this.quantity.changeUnQualifiedQuantity(unQualifiedQuantity);
        return this;
    }

    public WorkProcessQuantityEntity changeTransferQuantity(int transferQuantity){
        this.quantity = this.quantity.changeTransferQuantity(transferQuantity);
        return this;
    }

    public WorkProcessQuantityEntity changeOverCrossQuantity(int overCrossQuantity){
        this.quantity = this.quantity.changeOverCrossQuantity(overCrossQuantity);
        return this;
    }

    public WorkProcessQuantityEntity changeOwePaymentQuantity(int owePaymentQuantity) {
        this.quantity = this.quantity.changeOwePaymentQuantity(owePaymentQuantity);
        return this;
    }

    public WorkProcessQuantity getQuantity() {
        return quantity.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessQuantityEntity{" +
                "quantity=" + quantity +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
