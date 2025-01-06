package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.day.CalendarTypeValue;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.factory.domain.model.YearModel;
import org.ddd.fundamental.shared.api.factory.CalendarTypeDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Order(4)
public class YearModelAddable implements DataAddable {
    private final FactoryCommandService commandService;

    private final FactoryQueryService queryService;

    @Autowired(required = false)
    public YearModelAddable(FactoryCommandService commandService,
                            FactoryQueryService queryService){
        this.commandService = commandService;
        this.queryService = queryService;
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

    public static YearModel create(CalendarTypeValue calendarTypeValue){
        YearModel yearModel = YearModel.create(
                YearModelValue.createRandomShiftModel1("随机班次年度模型",2025,
                        calendarTypeValue
                )
        );
        return yearModel;
    }

    public List<YearModel> createYearModels(){
        List<CalendarTypeDTO> calendarTypeDTOS = queryService.calendarTypes();
        List<YearModel> result = new ArrayList<>();
        Generators.fill(result,()->
                create(CollectionUtils.random(calendarTypeDTOS).getCalendarType()
                ),20);
        return result;
    }

    @Override
    @Transactional
    public void execute() {
        this.commandService.batchAddYearModels(createYearModels());
    }
}
