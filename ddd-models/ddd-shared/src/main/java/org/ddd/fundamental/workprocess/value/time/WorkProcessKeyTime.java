package org.ddd.fundamental.workprocess.value.time;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * 工序关键时间(跟工序数据有关而跟工序模板无关)
 */
@MappedSuperclass
@Embeddable
public class WorkProcessKeyTime implements ValueObject, Cloneable {

    private Instant startTime;

    private Instant endTime;

    private Instant interruptTime;

    private Instant restartTime;

    private String reason;

    @SuppressWarnings("unused")
    private WorkProcessKeyTime(){}

    private WorkProcessKeyTime(Instant startTime,
                               Instant endTime,
                               Instant interruptTime,
                               String reason,
                               Instant restartTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.interruptTime = interruptTime;
        this.restartTime = restartTime;
        this.reason = reason;
    }

    private WorkProcessKeyTime(Instant startTime){
        this.startTime = startTime;
        this.endTime = null;
        this.interruptTime = null;
        this.reason = "";
    }

    public static WorkProcessKeyTime start(){
        return new WorkProcessKeyTime(Instant.now());
    }

    public static WorkProcessKeyTime start(Instant startTime){
        return new WorkProcessKeyTime(startTime);
    }

    public WorkProcessKeyTime finish(Instant endTime){
        if (endTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序结束时间不能早于工序开始时间");
        }
        if (null != this.interruptTime && endTime.isBefore(this.interruptTime)) {
            throw new RuntimeException("工序结束时间不能早于工序运行中的中断时间");
        }
        if (null != this.restartTime && endTime.isBefore(this.restartTime)) {
            throw new RuntimeException("工序结束时间不能早于工序运行中的重启时间");
        }
        this.endTime = endTime;
        return new WorkProcessKeyTime(startTime,endTime,interruptTime,reason,restartTime);
    }

    public WorkProcessKeyTime finish() {
        Instant endTime = Instant.now();
        return finish(endTime);
    }

    public WorkProcessKeyTime interrupt() {
        Instant interruptTime = Instant.now();
        return interrupt(interruptTime);
    }

    public WorkProcessKeyTime interrupt(Instant interruptTime) {
        if (interruptTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序中断时间不能早于工序开始时间");
        }
        this.interruptTime = interruptTime;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime);
    }

    public WorkProcessKeyTime changeReason(String reason) {
        this.reason = reason;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime);
    }

    public WorkProcessKeyTime restart() {
        Instant restartTime = Instant.now();
        return restart(restartTime);
    }

    public WorkProcessKeyTime restart(Instant restartTime) {
        if (restartTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序重启时间不能早于工序开始时间");
        }
        if (null == this.interruptTime) {
            throw new RuntimeException("工序没有中断过,不需要重启");
        }
        if (null != this.interruptTime && restartTime.isBefore(this.interruptTime)) {
            throw new RuntimeException("工序重启时间不能早于工序中断时间");
        }
        this.restartTime = restartTime;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime);
    }

    public long interruptRange() {
        return Duration.between(interruptTime,restartTime).toMinutes();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Instant getInterruptTime() {
        return interruptTime;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessKeyTime)) return false;
        WorkProcessKeyTime that = (WorkProcessKeyTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime)
                && Objects.equals(interruptTime, that.interruptTime) && Objects.equals(reason, that.reason)
                && Objects.equals(restartTime, that.restartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, interruptTime, reason,restartTime);
    }

    @Override
    public String toString() {
        return "WorkProcessKeyTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", interruptTime=" + interruptTime +
                ", restartTime=" + restartTime +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public WorkProcessKeyTime clone() {
        try {
            WorkProcessKeyTime clone = (WorkProcessKeyTime) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
