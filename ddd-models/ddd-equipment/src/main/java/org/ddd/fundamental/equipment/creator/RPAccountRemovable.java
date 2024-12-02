package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class RPAccountRemovable implements DataRemovable {

    private final RPAccountRepository accountRepository;

    private final RedisStoreManager manager;

    public RPAccountRemovable(@Autowired RPAccountRepository accountRepository,
                              @Autowired RedisStoreManager manager){
        this.accountRepository = accountRepository;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("delete all RPAccounts from db start");
        this.accountRepository.deleteAllRPAccounts();
        log.info("delete all RPAccounts from db finished");
        log.info("delete all RPAccounts from cache start");
        this.manager.deleteAllData(RPAccountDTO.class);
        log.info("delete all RPAccounts from cache finished");
    }
}
