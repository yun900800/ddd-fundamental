package org.ddd.fundamental.workprocess.rest;

import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.workprocess.application.WorkProcessTemplateApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkProcessTemplateRest {

    @Autowired
    private WorkProcessTemplateApplication application;

    @RequestMapping("/process/process-templates")
    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates(){
        return application.craftsmanShipTemplates();
    }
}
