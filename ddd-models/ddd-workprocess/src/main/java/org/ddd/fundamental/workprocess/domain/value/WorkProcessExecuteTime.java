package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkProcessExecuteTime implements ValueObject,Cloneable {

    /**
     * 设置时间
     */
    private DateRangeValue setTime;

    /**
     * 加工时间
     */
    private DateRangeValue workTime;

    /**
     * 下线时间
     */
    private DateRangeValue offlineTime;

    @SuppressWarnings("unused")
    protected WorkProcessExecuteTime(){
    }

    protected WorkProcessExecuteTime(DateRangeValue setTime,
                                   DateRangeValue workTime,
                                   DateRangeValue offlineTime){
        this.setTime = setTime;
        this.workTime = workTime;
        this.offlineTime = offlineTime;
    }

    public static WorkProcessExecuteTime create(DateRangeValue setTime,
                                                DateRangeValue workTime,
                                                DateRangeValue offlineTime){
        return new WorkProcessExecuteTime(setTime,workTime,offlineTime);
    }

    public WorkProcessExecuteTime startSetTime() {
        this.setTime = DateRangeValue.start();
        return this;
    }

    public WorkProcessExecuteTime finishSetTime(String reason){
        this.setTime.finish(reason);
        return this;
    }

    private void setTimeIsValid() {
        if (null == this.setTime){
            throw new RuntimeException("请先创建一个设置时间");
        }
    }

    private void workTimeIsValid() {
        if (null == this.workTime){
            throw new RuntimeException("请先创建一个加工时间");
        }
    }

    private void offlineTimeIsValid(){
        if (null == this.offlineTime){
            throw new RuntimeException("请先创建一个下线时间");
        }
    }

    public WorkProcessExecuteTime startWork(){
        setTimeIsValid();
        this.workTime = DateRangeValue.start();
        return this;
    }

    public WorkProcessExecuteTime finishWork(){
        setTimeIsValid();
        workTimeIsValid();
        this.workTime.finish("finish work");
        return this;
    }

    public WorkProcessExecuteTime startOffline(){
        setTimeIsValid();
        workTimeIsValid();
        this.offlineTime = DateRangeValue.start();
        return this;
    }

    public WorkProcessExecuteTime finishOffLine(String reason){
        setTimeIsValid();
        workTimeIsValid();
        offlineTimeIsValid();
        this.offlineTime.finish(reason);
        return this;
    }

    public WorkProcessExecuteTime changeSetTime(DateRangeValue setTime){
        if ((this.workTime != null && setTime.isAfterRange(this.workTime)) ||
                (this.offlineTime !=null &&setTime.isAfterRange(this.offlineTime))) {
            throw new RuntimeException("设置时间不能在加工时间或者下线时间之后");
        }
        this.setTime = setTime;
        return this;
    }

    public WorkProcessExecuteTime changeWorkTime(DateRangeValue workTime){
        if ((null != this.setTime && workTime.isBeforeRange(this.setTime)) ||
                (null != this.offlineTime && workTime.isAfterRange(this.offlineTime) )){
            throw new RuntimeException("加工时间需要在设置时间和下线时间之间");
        }
        this.workTime = workTime;
        return this;
    }

    public WorkProcessExecuteTime changeOffLineTime(DateRangeValue offlineTime){
        if ((null != this.setTime &&offlineTime.isBeforeRange(this.setTime)) ||
                (null != this.workTime && offlineTime.isBeforeRange(this.workTime))){
            throw new RuntimeException("下线时间需要在设置时间和加工之间之后");
        }
        this.offlineTime = offlineTime;
        return this;
    }

    public DateRangeValue getSetTime() {
        if (null == setTime) {
            return null;
        }
        return setTime.clone();
    }

    public DateRangeValue getWorkTime() {
        if (null == workTime) {
            return null;
        }
        return workTime.clone();
    }

    public DateRangeValue getOfflineTime() {
        if (null == offlineTime) {
            return null;
        }
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
