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
    private CalendarTypeValue calendarType;

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
        this.calendarType = dayType;
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

    public CalendarTypeValue getCalendarType() {
        return calendarType;
    }

    public DayOff getDayOff() {
        return dayOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearModelValue)) return false;
        YearModelValue yearModel = (YearModelValue) o;
        return Objects.equals(calendarType, yearModel.calendarType) && Objects.equals(dayOff, yearModel.dayOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarType, dayOff);
    }

    @Override
    public String toString() {
        return "YearModel{" +
                "modelName='" + modelName + '\'' +
                ", hasWeekend=" + hasWeekend  +
                ", dayType=" + calendarType +
                ", dayOff=" + dayOff +
                '}';
    }

    private long days(){
        long total = 365;
        long vocation = dayOff.getDays();
        long weekend = 0;
        if (hasWeekend) {
            weekend = 52 * 2;
        }
        return total - vocation - weekend;
    }

    @Override
    public long minutes() {
        return days() * calendarType.minutes();
    }

    public double hours(){
        return days() * calendarType.hours();
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
