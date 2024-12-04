package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.infra.hibernate.comment.Comment;
import org.ddd.fundamental.workprocess.value.WorkProcessTimeId;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "w_work_process_time")
@Comment("这是一个管理时间的表")
public class WorkProcessTimeEntity extends AbstractEntity<WorkProcessTimeId> {

    @Embedded
    private WorkProcessKeyTime keyTime;

    @Comment("备注信息")
    private String comment;

    @SuppressWarnings("unused")
    protected WorkProcessTimeEntity(){}

    private WorkProcessTimeEntity(WorkProcessKeyTime keyTime){
        super(WorkProcessTimeId.randomId(WorkProcessTimeId.class));
        this.keyTime = keyTime;
    }

    public WorkProcessKeyTime getKeyTime() {
        return keyTime;
    }

    @Override
    public String toString() {
        return "WorkProcessTimeEntity{" +
                "keyTime=" + keyTime +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
