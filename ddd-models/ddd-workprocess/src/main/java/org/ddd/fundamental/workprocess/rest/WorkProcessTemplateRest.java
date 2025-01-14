package org.ddd.fundamental.workprocess.rest;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.application.command.WorkProcessTemplateCommandService;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateQueryService;
import org.ddd.fundamental.workprocess.enums.PreOrNextType;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControlValue;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class WorkProcessTemplateRest {

    @Autowired
    private WorkProcessTemplateQueryService application;

    @Autowired
    private WorkProcessTemplateCommandService service;

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

    @RequestMapping("/process/testMemoize")
    public void testMemoize(){
        service.testMemoize();
    }

    @PostMapping("/process/change_beat/{id}")
    public void changeWorkProcessBeat(@RequestBody WorkProcessBeat beat, @PathVariable String id){
        service.changeWorkProcessBeat(beat,new WorkProcessTemplateId(id));
    }

    @PostMapping("/process/change_quantity/{id}")
    public void changeWorkProcessTemplateQuantity(@RequestBody WorkProcessTemplateQuantity quantity,
                                                  @PathVariable String id){
        service.changeWorkProcessTemplateQuantity(quantity,new WorkProcessTemplateId(id));
    }

    @PostMapping("/process/add_control/{id}")
    public void addControlInfoToTemplate(@RequestBody WorkProcessTemplateControlValue control,
                                         @PathVariable String id){
        service.addControlInfoToTemplate(control,new WorkProcessTemplateId(id));
    }

    @PostMapping("/process/add_resource/{id}")
    public void addProductResource(@RequestBody ProductResource resource,
                                   @PathVariable String id){
        service.addProductResource(resource,new WorkProcessTemplateId(id));
    }

    @PostMapping("/process/configure_template_preId/{templateId}/{preId}")
    public void configurePreId(@PathVariable String templateId,
                               @PathVariable String preId){
        service.configurePreOrNextId(new WorkProcessTemplateId(templateId), new WorkProcessTemplateId(preId),
                PreOrNextType.PRE);
    }

    @PostMapping("/process/configure_template_nextId/{templateId}/{nextId}")
    public void configureNextId(@PathVariable String templateId,
                               @PathVariable String nextId){
        service.configurePreOrNextId(new WorkProcessTemplateId(templateId), new WorkProcessTemplateId(nextId),
                PreOrNextType.NEXT);
    }

    @PostMapping("/process/remove_template_preId/{templateId}/{preId}")
    public void removePreId(@PathVariable String templateId,
                               @PathVariable String preId){
        service.removePreOrNextId(new WorkProcessTemplateId(templateId), new WorkProcessTemplateId(preId),
                PreOrNextType.PRE);
    }

    @PostMapping("/process/remove_template_nextId/{templateId}/{nextId}")
    public void removeNextId(@PathVariable String templateId,
                                @PathVariable String nextId){
        service.removePreOrNextId(new WorkProcessTemplateId(templateId), new WorkProcessTemplateId(nextId),
                PreOrNextType.NEXT);
    }
}
