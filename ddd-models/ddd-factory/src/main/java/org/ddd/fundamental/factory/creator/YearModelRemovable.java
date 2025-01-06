package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
@Order(4)
public class YearModelRemovable implements DataRemovable {

    private final FactoryCommandService commandService;

    @Autowired(required = false)
    public YearModelRemovable(FactoryCommandService commandService){
        this.commandService = commandService;
    }

    @Override
    @Transactional
    public void execute() {
        commandService.deleteAllYearModel();
    }
}
