package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.domain.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(1)
public class ProductionLineRemovable implements DataRemovable {

    @Autowired(required = false)
    private ProductionLineRepository repository;

    @Autowired(required = false)
    private WorkStationRepository workStationRepository;
    @Override
    @Transactional
    public void execute() {
        log.info("ProductionLineRemovable execute delete all ProductionLines");
        workStationRepository.deleteWorkStations();
        repository.deleteProductionLines();
        log.info("finished");
    }
}
