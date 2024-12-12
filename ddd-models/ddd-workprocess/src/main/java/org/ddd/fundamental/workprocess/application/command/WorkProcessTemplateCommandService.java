package org.ddd.fundamental.workprocess.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateApplication;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplateControlEntity;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
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

    /**
     * 修改工序模板的节拍
     * @param beat
     * @param templateId
     */
    @Transactional
    public void changeWorkProcessBeat(WorkProcessBeat beat, WorkProcessTemplateId templateId){
        WorkProcessTemplate processTemplate = templateRepository.findById(templateId).orElse(null);
        if (null == processTemplate) {
            return;
        }
        processTemplate.changeWorkProcessBeat(beat);
    }

    @Transactional
    public void changeWorkProcessTemplateQuantity(WorkProcessTemplateQuantity quantity, WorkProcessTemplateId templateId){
        WorkProcessTemplate processTemplate = templateRepository.findById(templateId).orElse(null);
        if (null == processTemplate) {
            return;
        }
        processTemplate.changeQuantity(quantity);
    }

    @Transactional
    public void addControlInfoToTemplate(WorkProcessTemplateControl control,WorkProcessTemplateId templateId){
        WorkProcessTemplate processTemplate = templateRepository.findById(templateId).orElse(null);
        if (null == processTemplate) {
            return;
        }
        processTemplate.setControl(WorkProcessTemplateControlEntity.create(control));
        //templateRepository.save(processTemplate);
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
