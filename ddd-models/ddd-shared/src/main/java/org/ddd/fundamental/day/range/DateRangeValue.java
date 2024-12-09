package org.ddd.fundamental.day.range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.CalculateTime;
import org.ddd.fundamental.tuple.ThreeTuple;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 日期间隔的差值
 * 计算日期间隔是否包含
 * 支持字段存储和json存储
 */
@MappedSuperclass
@Embeddable
@Slf4j
public class DateRangeValue implements ValueObject,Cloneable, CalculateTime {

    @Column(name = "range_start" , nullable = false)
    private Instant start;

    @Column(name = "range_end" , nullable = false)
    private Instant end;

    @Column(name = "description" , nullable = false)
    private String desc;

    @Transient
    private final Map<DateRangeValue,ThreeTuple<Integer,Long,Long>>
            cache = new HashMap<>();

    @Transient
    @JsonIgnore
    private Range<Instant> range;

    public DateRangeValue(){}

    private DateRangeValue(Instant start, Instant end, String desc){
        this.start = start;
        this.end = end;
        this.desc = desc;
        this.range = Range.between(start,end);
    }

    public static DateRangeValue create(Instant start, Instant end, String desc){
        return new DateRangeValue(start,end,desc);
    }

    public static DateRangeValue createByDuration(Instant start, int seconds){
        Instant end = start.plusSeconds(seconds);
        return new DateRangeValue(start,end,"range is "+seconds);
    }

    public static DateRangeValue start(){
        Instant current = Instant.now();
        return new DateRangeValue(current,current,"");
    }

    public DateRangeValue finish(String reason){
        if (start != end ) {
            throw new RuntimeException("这个时间段已经结束,请检查");
        }
        this.end = Instant.now();
        this.desc = reason;
        return this;
    }

    public ThreeTuple<Integer,Long,Long> range(){
        if (cache.containsKey(this)) {
            return cache.get(this);
        } else {
            LocalDateTime dtStart = LocalDateTime.ofInstant(start,ZoneId.systemDefault());
            LocalDateTime dtEnd = LocalDateTime.ofInstant(end,ZoneId.systemDefault());
            LocalDate dStart = dtStart.toLocalDate();
            LocalDate dEnd = dtEnd.toLocalDate();
            int date = Period.between(dStart,dEnd).getDays();
            LocalTime dStartTime = dtStart.toLocalTime();
            LocalTime dEndTime = dtEnd.toLocalTime();
            long minutes = Duration.between(dStartTime,dEndTime).toMinutes();
            long seconds = Duration.between(dStartTime,dEndTime).toSeconds();
            log.info("data need calculate again");
            ThreeTuple<Integer,Long,Long> result = Tuple.tuple(date,minutes,seconds);
            cache.put(this, result);
            return result;
        }

    }

    @Override
    public long minutes() {
        TwoTuple<Integer,Long> twoTuple = range();
        return twoTuple.first * 24 * 60 + twoTuple.second;
    }

    public boolean containsRange(DateRangeValue other) {
        return this.range.containsRange(other.range);
    }

    /**
     * 不包含边界的情况
     * @param other
     * @return
     */
    public boolean isAfterRange(DateRangeValue other){
        return this.range.isAfterRange(other.range);
    }

    /**
     * 不包含边界的情况
     * @param other
     * @return
     */
    public boolean isBeforeRange(DateRangeValue other) {
        return this.range.isBeforeRange(other.range);
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRangeValue)) return false;
        DateRangeValue that = (DateRangeValue) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, desc);
    }

    @Override
    public String toString() {
        return "DateRangeValue{" +
                "start=" + start +
                ", end=" + end +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public DateRangeValue clone() {
        try {
            DateRangeValue clone = (DateRangeValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
