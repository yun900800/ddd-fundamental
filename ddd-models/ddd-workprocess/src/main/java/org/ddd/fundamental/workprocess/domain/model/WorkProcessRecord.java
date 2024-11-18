package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
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
     * 工序资源,时间,数量信息
     */
    @Embedded
    private WorkProcessValue workProcessValue;


    @SuppressWarnings("unused")
    private WorkProcessRecord(){}

    private WorkProcessRecord(ChangeableInfo processInfo,
                              WorkProcessValue workProcessValue){
        super(WorkProcessId.randomId(WorkProcessId.class));
        this.processInfo = processInfo;
        this.workProcessValue = workProcessValue;
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
