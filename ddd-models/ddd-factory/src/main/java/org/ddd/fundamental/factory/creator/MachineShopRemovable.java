package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(2)
public class MachineShopRemovable implements DataRemovable {
    @Autowired
    private MachineShopRepository machineShopRepository;

    @Override
    @Transactional
    public void execute() {
        log.info("MachineShopRemovable execute delete all MachineShops");
        machineShopRepository.deleteAllInBatch();
        log.info("finish");
    }
}
