package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工单控制
 */
@MappedSuperclass
@Embeddable
public class WorkOrderControl implements ValueObject, Cloneable {
    /**
     * 是否需要排产
     */
    private boolean isSchedule;

    /**
     * 是否需要报工
     */
    private boolean isReportWork;

    /**
     * 是否允许委外工序
     */

    @SuppressWarnings("unused")
    private boolean isAllowedOutSourcing;

    private WorkOrderControl(){}

    private WorkOrderControl(boolean isSchedule,
                             boolean isReportWork,
                             boolean isAllowedOutSourcing) {
        this.isSchedule = isSchedule;
        this.isReportWork = isReportWork;
        this.isAllowedOutSourcing = isAllowedOutSourcing;
    }

    public static WorkOrderControl create(boolean isSchedule,
                                          boolean isReportWork,
                                          boolean isAllowedOutSourcing){
        return new WorkOrderControl(isSchedule, isReportWork,
                isAllowedOutSourcing);
    }

    public boolean isSchedule() {
        return isSchedule;
    }

    public boolean isReportWork() {
        return isReportWork;
    }

    public boolean isAllowedOutSourcing() {
        return isAllowedOutSourcing;
    }

    @Override
    public String toString() {
        return "WorkOrderControl{" +
                "isSchedule=" + isSchedule +
                ", isReportWork=" + isReportWork +
                ", isAllowedOutSourcing=" + isAllowedOutSourcing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkOrderControl)) return false;
        WorkOrderControl that = (WorkOrderControl) o;
        return isSchedule == that.isSchedule && isReportWork == that.isReportWork && isAllowedOutSourcing == that.isAllowedOutSourcing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSchedule, isReportWork, isAllowedOutSourcing);
    }

    @Override
    public WorkOrderControl clone() {
        try {
            WorkOrderControl clone = (WorkOrderControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
