package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;

import javax.persistence.*;
import java.time.Instant;

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

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "work_process_time_id")
    private WorkProcessTimeEntity workProcessTime;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "quantity_id")
    private WorkProcessQuantityEntity quantity;


    @SuppressWarnings("unused")
    private WorkProcessRecord(){}

    private WorkProcessRecord(ChangeableInfo processInfo,
                              WorkProcessValue workProcessValue){
        super(WorkProcessId.randomId(WorkProcessId.class));
        this.processInfo = processInfo;
        this.workProcessValue = workProcessValue;
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
        this.workProcessTime = workProcessTime;
    }

    public static WorkProcessRecord create(ChangeableInfo processInfo,
                                           WorkProcessValue workProcessValue){
        return new WorkProcessRecord(processInfo, workProcessValue);
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
