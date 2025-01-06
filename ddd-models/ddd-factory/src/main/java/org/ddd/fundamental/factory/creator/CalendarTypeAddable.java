package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.day.CalendarTypeValue;
import org.ddd.fundamental.day.Shift;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.domain.model.CalendarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Order(3)
public class CalendarTypeAddable implements DataAddable {

    private final FactoryCommandService commandService;

    private static CalendarType create(){
        CalendarType calendarType = CalendarType.create(
                CalendarTypeValue.createRandomShift(
                        "随机日历类型",
                        LocalTime.of(6,0),LocalTime.of(22,0),
                        240,220
                )

        );
        return calendarType;
    }

    private static List<CalendarType> createCalendarList(){
        List<CalendarType> calendarTypes = new ArrayList<>();
        Generators.fill(calendarTypes,()->create(),50);
        return calendarTypes;
    }

    @Autowired(required = false)
    public CalendarTypeAddable(FactoryCommandService commandService){
        this.commandService = commandService;
    }
    @Override
    @Transactional
    public void execute() {
        this.commandService.batchAddCalendarTypes(createCalendarList());
    }
}
