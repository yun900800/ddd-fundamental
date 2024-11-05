package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRange;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkProcessExecuteTime implements ValueObject,Cloneable {

    /**
     * 设置时间
     */
    private DateRange setTime;

    /**
     * 加工时间
     */
    private DateRange workTime;

    /**
     * 下线时间
     */
    private DateRange offlineTime;

    @SuppressWarnings("unused")
    protected WorkProcessExecuteTime(){
    }

    protected WorkProcessExecuteTime(DateRange setTime,
                                   DateRange workTime,
                                   DateRange offlineTime){
        this.setTime = setTime;
        this.workTime = workTime;
        this.offlineTime = offlineTime;
    }

    public static WorkProcessExecuteTime create(DateRange setTime,
                                                DateRange workTime,
                                                DateRange offlineTime){
        return new WorkProcessExecuteTime(setTime,workTime,offlineTime);
    }

    public WorkProcessExecuteTime changeSetTime(DateRange setTime){
        this.setTime = setTime;
        return this;
    }

    public DateRange getSetTime() {
        return setTime.clone();
    }

    public DateRange getWorkTime() {
        return workTime.clone();
    }

    public DateRange getOfflineTime() {
        return offlineTime.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessExecuteTime)) return false;
        WorkProcessExecuteTime that = (WorkProcessExecuteTime) o;
        return Objects.equals(setTime, that.setTime) && Objects.equals(workTime, that.workTime) && Objects.equals(offlineTime, that.offlineTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setTime, workTime, offlineTime);
    }

    @Override
    public String toString() {
        return "WorkProcessExecuteTime{" +
                "setTime=" + setTime +
                ", workTime=" + workTime +
                ", offlineTime=" + offlineTime +
                '}';
    }

    @Override
    public WorkProcessExecuteTime clone() {
        try {
            WorkProcessExecuteTime clone = (WorkProcessExecuteTime) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
