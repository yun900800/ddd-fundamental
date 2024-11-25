package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRangeValue;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class WorkProcessCycle extends WorkProcessExecuteTime implements Cloneable{

    /**
     * 等待时间
     */
    private DateRangeValue waitTimeRange;
    @SuppressWarnings("unused")
    protected WorkProcessCycle(){

    }

    protected WorkProcessCycle(DateRangeValue waitTimeRange,
                               DateRangeValue setTime,
                               DateRangeValue workTime,
                               DateRangeValue offlineTime){
        super(setTime,workTime,offlineTime);
        this.waitTimeRange = waitTimeRange;
    }

    public static WorkProcessCycle create(DateRangeValue waitTimeRange,
                                          DateRangeValue setTime,
                                          DateRangeValue workTime,
                                          DateRangeValue offlineTime){
        return new WorkProcessCycle(waitTimeRange,setTime,workTime,offlineTime);
    }

    public WorkProcessCycle waitStart() {
        this.waitTimeRange = DateRangeValue.start();
        return this;
    }

    public WorkProcessCycle finishWait(String reason) {
        this.waitTimeRange.finish(reason);
        return this;
    }

    private void waitTimeIsValid() {
        if (this.waitTimeRange == null) {
            throw new RuntimeException("请先创建一个等待时间");
        }
    }

    public WorkProcessCycle startSetTime(){
        waitTimeIsValid();
        super.startSetTime();
        return this;
    }

    public WorkProcessCycle finishSetTime(String reason){
        waitTimeIsValid();
        super.finishSetTime(reason);
        return this;
    }

    public WorkProcessCycle startWork(){
        waitTimeIsValid();
        super.startWork();
        return this;
    }

    public WorkProcessCycle finishWork(){
        waitTimeIsValid();
        super.finishWork();
        return this;
    }

    public WorkProcessCycle startOffline(){
        waitTimeIsValid();
        super.startOffline();
        return this;
    }

    public WorkProcessCycle finishOffLine(String reason){
        waitTimeIsValid();
        super.finishOffLine(reason);
        return this;
    }

    public DateRangeValue getWaitTimeRange() {
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
