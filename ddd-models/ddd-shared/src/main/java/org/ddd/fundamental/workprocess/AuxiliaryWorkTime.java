package org.ddd.fundamental.workprocess;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRange;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class AuxiliaryWorkTime implements ValueObject, Cloneable {

    private DateRange setTime;

    private DateRange offlineTime;

    private DateRange checkTime;

    private AuxiliaryWorkTime(){}

    private AuxiliaryWorkTime(DateRange setTime,
                              DateRange offlineTime,
                              DateRange checkTime){
        this.setTime = setTime;
        this.offlineTime = offlineTime;
        this.checkTime = checkTime;
    }

    public static AuxiliaryWorkTime create(DateRange setTime,
                                           DateRange offlineTime,
                                           DateRange checkTime){
        return new AuxiliaryWorkTime(setTime, offlineTime,checkTime);
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
