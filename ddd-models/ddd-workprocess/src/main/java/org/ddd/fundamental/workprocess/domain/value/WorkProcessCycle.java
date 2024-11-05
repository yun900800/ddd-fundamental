package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRange;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class WorkProcessCycle extends WorkProcessExecuteTime implements Cloneable{

    /**
     * 等待时间
     */
    private DateRange waitTimeRange;
    @SuppressWarnings("unused")
    protected WorkProcessCycle(){

    }

    protected WorkProcessCycle(DateRange waitTimeRange,
                             DateRange setTime,
                             DateRange workTime,
                             DateRange offlineTime){
        super(setTime,workTime,offlineTime);
        this.waitTimeRange = waitTimeRange;
    }

    public static WorkProcessCycle create(DateRange waitTimeRange,
                                          DateRange setTime,
                                          DateRange workTime,
                                          DateRange offlineTime){
        return new WorkProcessCycle(waitTimeRange,setTime,workTime,offlineTime);
    }

    public DateRange getWaitTimeRange() {
        return waitTimeRange.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessCycle{" +
                "waitTimeRange=" + waitTimeRange +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessCycle)) return false;
        if (!super.equals(o)) return false;
        WorkProcessCycle that = (WorkProcessCycle) o;
        return Objects.equals(waitTimeRange, that.waitTimeRange) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), waitTimeRange);
    }

    @Override
    public WorkProcessCycle clone() {
        WorkProcessCycle clone = (WorkProcessCycle) super.clone();
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        return clone;
    }
}
