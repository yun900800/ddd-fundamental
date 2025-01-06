package org.ddd.fundamental.day;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@Slf4j
public class YearModelValue implements ValueObject, CalculateTime, Cloneable {

    private String modelName;

    private boolean hasWeekend;

    @Embedded
    private CalendarTypeValue calendarType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "year",column = @Column(name = "year_to_day", nullable = true))
    })
    private DayOff dayOff;

    private int year;

    @SuppressWarnings("unused")
    YearModelValue(){}

    private YearModelValue(CalendarTypeValue dayType, DayOff off,
                           String modelName, int year){
        this(dayType,off, modelName,year,true);
    }

    private YearModelValue(CalendarTypeValue dayType, DayOff off,
                           String modelName, int year, boolean hasWeekend){
        this.dayOff = off;
        this.calendarType = dayType;
        this.modelName = modelName;
        this.hasWeekend = hasWeekend;
        this.year = year;
    }

    public String getModelName() {
        return modelName;
    }

    public boolean isHasWeekend() {
        return hasWeekend;
    }

    public static YearModelValue createTwoShift(String modelName, int year){
        return new YearModelValue(CalendarTypeValue.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(year),modelName,year);
    }

    public static YearModelValue createThreeShift(String modelName, int year){
        return new YearModelValue(CalendarTypeValue.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(year),modelName,year);
    }

    public static YearModelValue createTwoShift(String modelName, boolean hasWeekend, int year){
        return new YearModelValue(CalendarTypeValue.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(year),modelName,year,hasWeekend);
    }

    public static YearModelValue createThreeShift(String modelName, boolean hasWeekend, int year){
        return new YearModelValue(CalendarTypeValue.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(year),modelName,year,hasWeekend);
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
        return objToString();
    }

    private long days(){
        long total = 365;
        //休息的日期,包括节假期和周末
        long vocation = dayOff.getDays();
        if (hasWeekend) {
            log.info("has weekend days is {}",(total - vocation));
            return total - vocation;
        } else {
            long weekend = DayOff.getWeekends(dayOff.getYear()).size();
            log.info("no weekend days is {}",(total - vocation + weekend));
            return total - vocation + weekend;
        }
    }

    @Override
    public long minutes() {
        return days() * calendarType.minutes();
    }

    public double hours(){
        log.info("day hour is {}",calendarType.hours());
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
