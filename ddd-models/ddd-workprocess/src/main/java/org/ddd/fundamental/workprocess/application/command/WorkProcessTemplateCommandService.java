package org.ddd.fundamental.workprocess.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateApplication;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class WorkProcessTemplateCommandService {

    private final WorkProcessTemplateRepository templateRepository;

    private final WorkProcessTemplateApplication application;

    @Autowired
    public WorkProcessTemplateCommandService(WorkProcessTemplateRepository templateRepository,
                                             WorkProcessTemplateApplication application){
        this.templateRepository = templateRepository;
        this.application = application;
    }

    @Transactional
    public void testMemoize(){
        List<WorkProcessTemplateDTO> templateDTOs = application.workProcessTemplates();
        List<String> ids = Arrays.asList(
                templateDTOs.get(0).id().toUUID(),
                templateDTOs.get(1).id().toUUID(),
                templateDTOs.get(5).id().toUUID()
        );
        List<WorkProcessTemplate> rangeTemplateDTOs = application.queryByIds(ids);
        List<WorkProcessTemplate> rangeTemplateDTOs1 = application.queryByIds(ids);
        log.info("test Memoize");
    }
}
