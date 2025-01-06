package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class YearModelValue implements ValueObject, CalculateTime,Cloneable {

    private String modelName;

    private boolean hasWeekend;

    @Embedded
    private CalendarTypeValue dayType;

    @Embedded
    private DayOff dayOff;

    @SuppressWarnings("unused")
    YearModelValue(){}

    private YearModelValue(CalendarTypeValue dayType, DayOff off,
                           String modelName){
        this(dayType,off, modelName,true);
    }

    private YearModelValue(CalendarTypeValue dayType, DayOff off,
                           String modelName, boolean hasWeekend){
        this.dayOff = off;
        this.dayType = dayType;
        this.modelName = modelName;
        this.hasWeekend = hasWeekend;
    }

    public String getModelName() {
        return modelName;
    }

    public boolean isHasWeekend() {
        return hasWeekend;
    }

    public static YearModelValue createTwoShift(String modelName){
        return new YearModelValue(CalendarTypeValue.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(),modelName);
    }

    public static YearModelValue createThreeShift(String modelName){
        return new YearModelValue(CalendarTypeValue.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(),modelName);
    }

    public static YearModelValue createTwoShift(String modelName, boolean hasWeekend){
        return new YearModelValue(CalendarTypeValue.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(),modelName,hasWeekend);
    }

    public static YearModelValue createThreeShift(String modelName, boolean hasWeekend){
        return new YearModelValue(CalendarTypeValue.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(),modelName,hasWeekend);
    }

    public CalendarTypeValue getDayType() {
        return dayType;
    }

    public DayOff getDayOff() {
        return dayOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearModelValue)) return false;
        YearModelValue yearModel = (YearModelValue) o;
        return Objects.equals(dayType, yearModel.dayType) && Objects.equals(dayOff, yearModel.dayOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayType, dayOff);
    }

    @Override
    public String toString() {
        return "YearModel{" +
                "modelName='" + modelName + '\'' +
                ", hasWeekend=" + hasWeekend  +
                ", dayType=" + dayType +
                ", dayOff=" + dayOff +
                '}';
    }

    @Override
    public long minutes() {
        long total = 365;
        long vocation = dayOff.getDays();
        long weekend = 0;
        if (hasWeekend) {
            weekend = 52 * 2;
        }
        return (total - vocation - weekend ) * dayType.minutes() ;
    }


    @Override
    public YearModelValue clone() {
        try {
            YearModelValue clone = (YearModelValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
