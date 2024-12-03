package org.ddd.fundamental.workprocess.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(1)
public class CraftsmanShipRemovable implements DataRemovable {

    private final CraftsmanShipRepository craftsmanShipRepository;

    private final RedisStoreManager manager;

    @Autowired
    public CraftsmanShipRemovable(CraftsmanShipRepository craftsmanShipRepository,
                                  RedisStoreManager manager){
        this.craftsmanShipRepository = craftsmanShipRepository;
        this.manager = manager;
    }
    @Override
    @Transactional
    public void execute() {
        log.info("delete all CraftsmanShipTemplates start");
        craftsmanShipRepository.deleteCraftsmanShips();
        log.info("delete all CraftsmanShipTemplates finished");
        manager.deleteAllData(CraftsmanShipTemplateDTO.class);
    }
}
