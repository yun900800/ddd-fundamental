package org.ddd.fundamental.day.range;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class DateTimeRange implements ValueObject, Cloneable {

    private Instant start;

    private Instant end;

    @SuppressWarnings("unused")
    protected DateTimeRange(){}

    private DateTimeRange(Instant start, Instant end){
        this.start = start;
        this.end = end;
    }

    public long days(){
        return Duration.between(start,end).toDays();
    }

    public long minutes(){
        return Duration.between(start,end).toMinutes();
    }

    public static DateTimeRange create(Instant start, Instant end){
        return new DateTimeRange(start,end);
    }

    public static DateTimeRange createStartAfterDuration(Instant start, int days){
        Instant end = start.plus(days, ChronoUnit.DAYS);
        return create(start,end);
    }

    public static DateTimeRange createEndBeforeDuration(Instant end, int days){
        Instant start = end.plus(-days, ChronoUnit.DAYS);
        return create(start,end);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeRange)) return false;
        DateTimeRange that = (DateTimeRange) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    @Override
    public DateTimeRange clone() {
        try {
            DateTimeRange clone = (DateTimeRange) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
