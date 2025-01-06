package org.ddd.fundamental.shared.api.factory;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.day.CalendarTypeId;
import org.ddd.fundamental.day.CalendarTypeValue;

public class CalendarTypeDTO extends AbstractDTO<CalendarTypeId> {

    private CalendarTypeValue calendarType;

    @SuppressWarnings("unused")
    protected CalendarTypeDTO(){}

    private CalendarTypeDTO(CalendarTypeId id,
                            CalendarTypeValue calendarType){
        super(id);
        this.calendarType = calendarType;
    }

    public static CalendarTypeDTO create(CalendarTypeId id,
                                         CalendarTypeValue calendarType){
        return new CalendarTypeDTO(id,calendarType);
    }

    public CalendarTypeValue getCalendarType() {
        return calendarType;
    }

    @Override
    public CalendarTypeId id() {
        return null;
    }

    @Override
    public String toString() {
        return "CalendarTypeDTO{" +
                "calendarType=" + calendarType +
                ", id=" + id +
                '}';
    }
}
