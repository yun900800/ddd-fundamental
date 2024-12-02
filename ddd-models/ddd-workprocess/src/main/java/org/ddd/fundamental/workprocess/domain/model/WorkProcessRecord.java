package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;

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

    public WorkProcessRecord start() {
        this.workProcessValue = this.workProcessValue.start();
        return this;
    }

    public WorkProcessRecord start(Instant startTime) {
        this.workProcessValue = this.workProcessValue.start(startTime);
        return this;
    }


    public WorkProcessRecord finish() {
        this.workProcessValue = this.workProcessValue.finish();
        return this;
    }

    public WorkProcessRecord finish(Instant endTime) {
        this.workProcessValue = this.workProcessValue.finish(endTime);
        return this;
    }

    public WorkProcessRecord interrupt(){
        this.workProcessValue = this.workProcessValue.interrupt();
        return this;
    }

    public WorkProcessRecord interrupt(Instant interruptTime) {
        this.workProcessValue = this.workProcessValue.interrupt(interruptTime);
        return this;
    }

    public WorkProcessRecord restart() {
        this.workProcessValue = this.workProcessValue.restart();
        return this;
    }

    public WorkProcessRecord restart(Instant restartTime) {
        this.workProcessValue = this.workProcessValue.restart(restartTime);
        return this;
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
