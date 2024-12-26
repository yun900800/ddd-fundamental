package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(1)
public class RPAccountRemovable implements DataRemovable {

    private final EquipmentCommandService commandService;

    private final RedisStoreManager manager;

    @Autowired(required = false)
    public RPAccountRemovable(EquipmentCommandService commandService,
                              RedisStoreManager manager){
        this.commandService = commandService;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("delete all RPAccounts from db start");
        this.commandService.deleteAllRPAccount();
        log.info("delete all RPAccounts from db finished");
        log.info("delete all RPAccounts from cache start");
        this.manager.deleteAllData(RPAccountDTO.class);
        log.info("delete all RPAccounts from cache finished");
    }
}
