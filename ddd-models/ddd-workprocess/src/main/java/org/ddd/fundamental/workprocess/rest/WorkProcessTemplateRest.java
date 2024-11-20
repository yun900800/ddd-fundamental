package org.ddd.fundamental.workprocess.rest;

import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.application.WorkProcessTemplateApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkProcessTemplateRest {

    @Autowired
    private WorkProcessTemplateApplication application;

    @RequestMapping("/process/craftsmanShip-templates")
    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates(){
        return application.craftsmanShipTemplates();
    }

    @RequestMapping("/process/process-templates")
    public List<WorkProcessTemplateDTO> workProcessTemplates() {
        return application.workProcessTemplates();
    }

    @RequestMapping("/process/process-templates-ids")
    public List<WorkProcessTemplateDTO> workProcessTemplatesByIds(@RequestBody List<String> ids) {
        return application.workProcessTemplatesByIds(ids);
    }
}
