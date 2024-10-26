package org.ddd.fundamental.date;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

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
}
