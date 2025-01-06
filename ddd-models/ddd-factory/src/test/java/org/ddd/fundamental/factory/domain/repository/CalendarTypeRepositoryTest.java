package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.day.CalendarTypeId;
import org.ddd.fundamental.day.CalendarTypeValue;
import org.ddd.fundamental.day.Shift;
import org.ddd.fundamental.factory.FactoryAppTest;
import org.ddd.fundamental.factory.domain.model.CalendarType;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

public class CalendarTypeRepositoryTest extends FactoryAppTest {

    @Autowired(required = false)
    private CalendarTypeRepository calendarTypeRepository;

    private static TwoTuple<CalendarType, CalendarTypeId> createCalendarType(){
        CalendarType calendarType = CalendarType.create(
                CalendarTypeValue.create(
                        "测试日类型",
                        Shift.createFromEnd(
                                LocalTime.parse("10:00:00"),
                                4,"早班"
                        ),
                        Shift.createFromStart(
                                LocalTime.parse("11:00:00"),
                                4,"中班"
                        ),
                        Shift.createFromStart(
                                LocalTime.parse("16:00:00"),
                                4,"晚班"
                        )
                )

        );
        return Tuple.tuple(calendarType,calendarType.id());
    }

    @Test
    public void testCreateCalendarType(){
        TwoTuple<CalendarType, CalendarTypeId> twoTuple = createCalendarType();
        CalendarType calendarType = twoTuple.first;
        calendarTypeRepository.persist(calendarType);
        CalendarTypeId id = twoTuple.second;
        CalendarType query = calendarTypeRepository.findById(id).orElse(null);
        Assert.assertEquals(query.getCalendarType().minutes(),720);
        Assert.assertEquals(query.getCalendarType().getShiftList().size(),3);
    }

}
