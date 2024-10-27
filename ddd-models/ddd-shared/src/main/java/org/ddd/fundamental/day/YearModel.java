package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class YearModel implements ValueObject {

    @Embedded
    private DayType dayType;

    @Embedded
    private DayOff dayOff;

    @SuppressWarnings("unused")
    YearModel(){}

    public YearModel(DayType dayType, DayOff off){
        this.dayOff = off;
        this.dayType = dayType;
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
        if (!(o instanceof YearModel)) return false;
        YearModel yearModel = (YearModel) o;
        return Objects.equals(dayType, yearModel.dayType) && Objects.equals(dayOff, yearModel.dayOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayType, dayOff);
    }
}
