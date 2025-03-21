package org.ddd.fundamental.day.range;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.CalculateTime;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.DateTimeUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Slf4j
public class DateRange implements ValueObject, Cloneable, CalculateTime {

    @Column(name = "description" , nullable = false)
    private String desc;

    @Column(nullable = false)
    private Date start;

    @Column(nullable = false)
    private Date end;

    @Transient
    private final Map<DateRange,TwoTuple<Integer,Long>>
        cache = new HashMap<>();

    @SuppressWarnings("unused")
    private DateRange(){}

    public DateRange(Date start,Date end,String desc){
        this.start = start;
        this.end = end;
        this.desc = desc;
    }

    public TwoTuple<Integer,Long> range(){
        LocalDate dStart = DateTimeUtils.dataToLocalDate(start);
        LocalDate dEnd = DateTimeUtils.dataToLocalDate(end);
        int date = Period.between(dStart,dEnd).getDays();
        LocalTime dStartTime = DateTimeUtils.dataToLocalTime(start);
        LocalTime dEndTime = DateTimeUtils.dataToLocalTime(end);
        long minutes = Duration.between(dStartTime,dEndTime).toMinutes();
        if (!cache.containsKey(this)){
            log.info("data need calculate again");
            TwoTuple<Integer,Long> result = Tuple.tuple(date,minutes);
            cache.put(this, result);
        }
        return cache.get(this);
    }

    @Override
    public long minutes() {
        TwoTuple<Integer,Long> twoTuple = range();
        return twoTuple.first * 24 * 60 + twoTuple.second;
    }

    public String getDesc() {
        return desc;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange)) return false;
        DateRange range = (DateRange) o;
        return Objects.equals(desc, range.desc) && Objects.equals(start, range.start)
                && Objects.equals(end, range.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, start, end);
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "desc='" + desc + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public DateRange clone() {
        try {
            DateRange clone = (DateRange) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
