package org.ddd.fundamental.day.range;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.DateUtils;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.sql.Time;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Embeddable
@MappedSuperclass
@Slf4j
public class DateRange implements ValueObject, Cloneable {

    private String desc;

    private Range<Date> dateRange;

    @Transient
    private final Map<Range<Date>,TwoTuple<Integer,Long>>
        cache = new HashMap<>();

    @SuppressWarnings("unused")
    private DateRange(){}

    public DateRange(Date start,Date end,String desc){
        this.dateRange = Range.between(start,end);
        this.desc = desc;
    }

    public TwoTuple<Integer,Long> range(){
        LocalDate dStart = DateUtils.dataToLocalDate(dateRange.getMinimum());
        LocalDate dEnd = DateUtils.dataToLocalDate(dateRange.getMaximum());
        int date = Period.between(dStart,dEnd).getDays();
        LocalTime dStartTime = DateUtils.dataToLocalTime(dateRange.getMinimum());
        LocalTime dEndTime = DateUtils.dataToLocalTime(dateRange.getMaximum());
        long minutes = Duration.between(dStartTime,dEndTime).toMinutes();
        if (!cache.containsKey(dateRange)){
            log.info("data need calculate again");
            TwoTuple<Integer,Long> result = Tuple.tuple(date,minutes);
            cache.put(dateRange, result);
        }
        return cache.get(dateRange);
    }


    public String getDesc() {
        return desc;
    }

    public Range<Date> getDateRange() {
        return Range.between(dateRange.getMinimum(),dateRange.getMaximum());
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
