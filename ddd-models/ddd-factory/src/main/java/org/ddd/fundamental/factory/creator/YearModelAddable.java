package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.day.CalendarTypeValue;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.domain.model.YearModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Component
@Slf4j
@Order(4)
public class YearModelAddable implements DataAddable {
    private final FactoryCommandService commandService;

    @Autowired(required = false)
    public YearModelAddable(FactoryCommandService commandService){
        this.commandService = commandService;
    }

    public static YearModel create(){
        YearModel yearModel = YearModel.create(
                YearModelValue.createRandomShiftModel("三班年度模型",2025,
                        "随机日历类型",
                        LocalTime.of(6,0),LocalTime.of(22,0),
                        240,220

                )
        );
        return yearModel;
    }

    @Override
    @Transactional
    public void execute() {
        this.commandService.addYearModel(create());
    }
}
