package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
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

@Component
@Slf4j
@Order(3)
public class CalendarTypeAddable implements DataAddable {

    private final FactoryCommandService commandService;

    private static CalendarType create(){
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
        return calendarType;
    }

    @Autowired(required = false)
    public CalendarTypeAddable(FactoryCommandService commandService){
        this.commandService = commandService;
    }
    @Override
    @Transactional
    public void execute() {
        this.commandService.addCalendarType(create());
    }
}
