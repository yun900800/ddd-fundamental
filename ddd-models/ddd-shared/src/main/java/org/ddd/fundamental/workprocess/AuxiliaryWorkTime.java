package org.ddd.fundamental.workprocess;

import org.apache.commons.lang3.Range;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Embeddable
@MappedSuperclass
public class AuxiliaryWorkTime implements ValueObject, Cloneable {
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "set_time_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "set_time_end", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "set_time_desc", nullable = false))
    })
    private DateRange setTime;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "offline_time_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "offline_time_end", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "offline_time_desc", nullable = false))
    })
    private DateRange offlineTime;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "check_time_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "check_time_end", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "check_time_desc", nullable = false))
    })
    private DateRange checkTime;

    private AuxiliaryWorkTime(){}

    private AuxiliaryWorkTime(DateRange setTime,
                              DateRange offlineTime,
                              DateRange checkTime){
        notIntersection(setTime,offlineTime,checkTime);
        this.setTime = setTime;
        this.offlineTime = offlineTime;
        this.checkTime = checkTime;
    }

    public static AuxiliaryWorkTime create(DateRange setTime,
                                           DateRange offlineTime,
                                           DateRange checkTime){
        return new AuxiliaryWorkTime(setTime, offlineTime,checkTime);
    }

    private void notIntersection(DateRange setTime,
                                 DateRange offlineTime,
                                 DateRange checkTime) {
        Range<Date> setTimeRange = Range.between(setTime.getStart(),setTime.getEnd());
        Range<Date> offlineTimeRange = Range.between(offlineTime.getStart(),offlineTime.getEnd());
        Range<Date> checkTimeRange = Range.between(checkTime.getStart(),checkTime.getEnd());
        if (!isRangeOk(setTimeRange,offlineTimeRange)) {
            throw new RuntimeException("设置时间与下线时间存在交叉");
        }
        if (!isRangeOk(offlineTimeRange,checkTimeRange)) {
            throw new RuntimeException("下线时间与检查时间与存在交叉");
        }
        if (!isRangeOk(setTimeRange,checkTimeRange)) {
            throw new RuntimeException("设置时间与检查时间与存在交叉");
        }
    }

    private static boolean isRangeOk(Range<Date> firstRange, Range<Date> secondRange) {
        return firstRange.isAfterRange(secondRange) || firstRange.isBeforeRange(secondRange);
    }

    public AuxiliaryWorkTime changeSetTime(DateRange setTime) {
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public AuxiliaryWorkTime changeSetTime(Date end){
        Date start = new Date();
        DateRange setTime = new DateRange(start,end,"工序设置时间");
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public AuxiliaryWorkTime changeOffLineTime(DateRange offlineTime) {
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public AuxiliaryWorkTime changeOffLineTime(Date end){
        Date start = new Date();
        DateRange offlineTime = new DateRange(start,end,"工序下线时间");
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public AuxiliaryWorkTime changeCheckTime(DateRange checkTime){
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public AuxiliaryWorkTime changeCheckTime(Date end){
        Date start = new Date();
        DateRange checkTime = new DateRange(start,end,"工序检查时间");
        return new AuxiliaryWorkTime(setTime,offlineTime,checkTime);
    }

    public DateRange getSetTime() {
        return setTime;
    }

    public DateRange getOfflineTime() {
        return offlineTime;
    }

    public DateRange getCheckTime() {
        return checkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuxiliaryWorkTime)) return false;
        AuxiliaryWorkTime that = (AuxiliaryWorkTime) o;
        return Objects.equals(setTime, that.setTime) && Objects.equals(offlineTime, that.offlineTime) && Objects.equals(checkTime, that.checkTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setTime, offlineTime, checkTime);
    }

    @Override
    public String toString() {
        return "AuxiliaryWorkTime{" +
                "setTime=" + setTime +
                ", offlineTime=" + offlineTime +
                ", checkTime=" + checkTime +
                '}';
    }

    @Override
    public AuxiliaryWorkTime clone() {
        try {
            AuxiliaryWorkTime clone = (AuxiliaryWorkTime) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
