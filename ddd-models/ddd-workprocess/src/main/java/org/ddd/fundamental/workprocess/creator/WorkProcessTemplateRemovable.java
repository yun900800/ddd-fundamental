package org.ddd.fundamental.workprocess.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateControlRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Slf4j
@Order(2)
public class WorkProcessTemplateRemovable implements DataRemovable {


    private final WorkProcessTemplateRepository templateRepository;

    private final WorkProcessTemplateControlRepository templateControlRepository;

    private final RedisStoreManager manager;

    @Autowired
    public WorkProcessTemplateRemovable(WorkProcessTemplateRepository templateRepository,
                                        WorkProcessTemplateControlRepository templateControlRepository,
                                        RedisStoreManager manager){
        this.templateRepository = templateRepository;
        this.templateControlRepository = templateControlRepository;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("deleting all WorkProcessTemplates start ");
        this.templateRepository.deleteTemplates();
        this.templateControlRepository.deleteAllTemplateControls();
        log.info("deleting all WorkProcessTemplates finished");
        this.manager.deleteAllData(WorkProcessTemplateDTO.class);
    }
}
