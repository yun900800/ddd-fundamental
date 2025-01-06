package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.day.CalendarTypeId;
import org.ddd.fundamental.day.CalendarTypeValue;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "calendar_type")
public class CalendarType extends AbstractEntity<CalendarTypeId> {

    private CalendarTypeValue calendarType;

    @SuppressWarnings("unused")
    protected CalendarType(){}

    private CalendarType(CalendarTypeValue calendarType){
        super(CalendarTypeId.randomId(CalendarTypeId.class));
        this.calendarType = calendarType;
    }

    public static CalendarType create(CalendarTypeValue calendarType){
        return new CalendarType(calendarType);
    }

    public CalendarTypeValue getCalendarType() {
        return calendarType;
    }

    @Override
    public long created() {
        return 0;
    }
}
