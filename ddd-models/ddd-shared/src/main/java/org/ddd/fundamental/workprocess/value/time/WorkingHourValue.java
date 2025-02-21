package org.ddd.fundamental.workprocess.value.time;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateTimeRange;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Slf4j
public class WorkingHourValue implements ValueObject, Cloneable {

    private double standardHours;

    private double actualHours;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "process_start")),
            @AttributeOverride(name = "end", column = @Column(name = "process_end"))
    })
    private DateTimeRange timeRange;

    protected WorkingHourValue(){}

    private WorkingHourValue(double standardHours,
                             double actualHours,
                             DateTimeRange timeRange){
        this.standardHours = standardHours;
        this.actualHours = actualHours;
        this.timeRange = timeRange;
    }

    public static WorkingHourValue create(double standardHours,
                                          double actualHours,
                                          DateTimeRange timeRange){
        return new WorkingHourValue(standardHours,actualHours,timeRange);
    }

    public static WorkingHourValue createFromStart(double standardHours){
        Instant start = Instant.now();
        return create(standardHours, 0,
                DateTimeRange.create(start,null));
    }

    public WorkingHourValue finish(Instant end){
        Instant start = timeRange.getStart();
        this.actualHours = Duration.between(start,end).toMinutes() / 60.0;
        this.timeRange = DateTimeRange.create(start,end);
        return this;
    }

    public WorkingHourValue finish(){
        Instant now = Instant.now();
        finish(now);
        return this;
    }

    public double getStandardHours() {
        return standardHours;
    }

    public double getActualHours() {
        return actualHours;
    }

    public DateTimeRange getTimeRange() {
        return timeRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingHourValue)) return false;
        WorkingHourValue that = (WorkingHourValue) o;
        return Double.compare(that.standardHours, standardHours) == 0
                && Double.compare(that.actualHours, actualHours) == 0 && Objects.equals(timeRange, that.timeRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standardHours, actualHours, timeRange);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public WorkingHourValue clone() {
        try {
            WorkingHourValue clone = (WorkingHourValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
