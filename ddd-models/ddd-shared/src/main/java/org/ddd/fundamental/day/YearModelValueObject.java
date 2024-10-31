package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class YearModelValueObject implements ValueObject, CalculateTime {

    private String modelName;

    private boolean hasWeekend;

    @Embedded
    private DayType dayType;

    @Embedded
    private DayOff dayOff;

    @SuppressWarnings("unused")
    YearModelValueObject(){}

    private YearModelValueObject(DayType dayType, DayOff off,
                                 String modelName){
        this(dayType,off, modelName,true);
    }

    private YearModelValueObject(DayType dayType, DayOff off,
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

    public static YearModelValueObject createTwoShift(String modelName){
        return new YearModelValueObject(DayType.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(),modelName);
    }

    public static YearModelValueObject createThreeShift(String modelName){
        return new YearModelValueObject(DayType.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(),modelName);
    }

    public static YearModelValueObject createTwoShift(String modelName, boolean hasWeekend){
        return new YearModelValueObject(DayType.createTwoShiftDateType("两班制"),
                DayOff.createDayOff(),modelName,hasWeekend);
    }

    public static YearModelValueObject createThreeShift(String modelName, boolean hasWeekend){
        return new YearModelValueObject(DayType.createThreeShiftDateType("三班制"),
                DayOff.createDayOff(),modelName,hasWeekend);
    }

    public DayType getDayType() {
        return dayType;
    }

    public DayOff getDayOff() {
        return dayOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearModelValueObject)) return false;
        YearModelValueObject yearModel = (YearModelValueObject) o;
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
}
