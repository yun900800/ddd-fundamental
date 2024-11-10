package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * 工序关键时间
 */
@MappedSuperclass
@Embeddable
public class WorkProcessKeyTime implements ValueObject, Cloneable {

    private Instant startTime;

    private Instant endTime;

    private Instant interruptTime;

    private String reason;

    @SuppressWarnings("unused")
    private WorkProcessKeyTime(){}

    private WorkProcessKeyTime(Instant startTime,
                               Instant endTime,
                               Instant interruptTime,
                               String reason){
        this.startTime = startTime;
        this.endTime = endTime;
        this.interruptTime = interruptTime;
        this.reason = reason;
    }

    private WorkProcessKeyTime(Instant startTime){
        this.startTime = startTime;
        this.endTime = null;
        this.interruptTime = null;
        this.reason = "";
    }

    public static WorkProcessKeyTime create(){
        return new WorkProcessKeyTime(Instant.now());
    }

    public static WorkProcessKeyTime create(Instant startTime){
        return new WorkProcessKeyTime(startTime);
    }

    public WorkProcessKeyTime changeEndTime() {
        this.endTime = Instant.now();
        return new WorkProcessKeyTime(startTime,endTime,interruptTime,reason);
    }

    public WorkProcessKeyTime changeInterruptTime() {
        this.interruptTime = Instant.now();
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason);
    }

    public WorkProcessKeyTime changeInterruptTime(Instant interruptTime) {
        this.interruptTime = interruptTime;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason);
    }

    public WorkProcessKeyTime changeReason(String reason) {
        this.reason = reason;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason);
    }

    public long interruptRange() {
        return Duration.between(startTime,interruptTime).toMinutes();
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
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(interruptTime, that.interruptTime) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, interruptTime, reason);
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
