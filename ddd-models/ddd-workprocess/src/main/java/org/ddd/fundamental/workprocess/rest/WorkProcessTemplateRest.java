package org.ddd.fundamental.workprocess.rest;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.application.WorkProcessTemplateApplication;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class WorkProcessTemplateRest {

    @Autowired
    private WorkProcessTemplateApplication application;

    @RequestMapping("/process/craftsmanShip-templates")
    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates(){
        return application.craftsmanShipTemplates();
    }

    @RequestMapping("/process/process-templates")
    public List<WorkProcessTemplateDTO> workProcessTemplates() {
//        List<WorkProcessTemplateDTO> templateDTOS = application.workProcessTemplates();
//        List<String> ids = templateDTOS.stream().map(v->v.id().toUUID()).collect(Collectors.toList());
//        List<WorkProcessTemplate> template1 = application.queryByIds(ids);
//        List<WorkProcessTemplate> template2 = application.queryByIds(ids);
//        log.info("template1 is {}",template1);
//        log.info("template2 is {}",template2);
        return application.workProcessTemplates();
    }

    @RequestMapping("/process/process-templates-ids")
    public List<WorkProcessTemplateDTO> workProcessTemplatesByIds(@RequestBody List<String> ids) {
        return application.workProcessTemplatesByIds(ids);
    }
}
