package org.ddd.fundamental.equipment.rest;

import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.equipment.*;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EquipmentRest {

    private final EquipmentCommandService commandService;


    private final EquipmentQueryService queryService;

    @Autowired(required = false)
    public EquipmentRest(EquipmentCommandService commandService,
                         EquipmentQueryService queryService){
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @RequestMapping("/equipment/equipments")
    public List<EquipmentDTO> equipments() {
        return queryService.equipments();
    }

    @RequestMapping("/equipment/toolingList")
    public List<ToolingDTO> toolingList() {
        return queryService.toolingList();
    }

    @RequestMapping("/equipment/rpAccountList")
    public List<RPAccountDTO> rpAccountList() {
        return queryService.rpAccountList();
    }

    @PostMapping("/equipment/create_equipment")
    public void createEquipment(@RequestBody EquipmentRequest request) {
        commandService.createEquipment(request.getMaster(),
                request.getModel(), request.getType(),
                request.getResource(), request.getResourceType());
    }

    @PostMapping("/equipment/add_accounts_to_equipment/{equipmentId}")
    public void addRpAccountsToEquipment(@PathVariable String equipmentId,
                                         @RequestBody List<RPAccountId> accountIds) {
        commandService.addRpAccountsToEquipment(new EquipmentId(equipmentId), accountIds);
    }

    @PostMapping("/equipment/add_account_to_equipment/{equipmentId}/{accountId}")
    public void addRpAccountToEquipment(@PathVariable String equipmentId,
                                        @PathVariable String accountId,
                                        @RequestBody BusinessRange<WorkOrderComposable> value) {
        commandService.addRpAccountToEquipment(new EquipmentId(equipmentId),
                new RPAccountId(accountId), value);
    }

    @PostMapping("/equipment/add_tooling_to_equipment/{equipmentId}/{toolingId}")
    public void addToolingToEquipment(@PathVariable String toolingId,
                                      @PathVariable String equipmentId) {
        commandService.addToolingToEquipment(new EquipmentId(toolingId), new EquipmentId(equipmentId));
    }

    @PostMapping("/equipment/add_plan_to_equipment/{equipmentId}")
    public void addBusinessPlanRangeToEquipment(@PathVariable String equipmentId,
                                                @RequestBody BusinessRange<WorkOrderComposable> addedValue) {
        commandService.addBusinessPlanRangeToEquipment(new EquipmentId(equipmentId), addedValue);
    }

    @PostMapping("/equipment/configure_material_input_output/{equipmentId}")
    public void configureEquipmentInputAndOutput(@PathVariable String equipmentId,
                                                 @RequestBody ConfigureMaterialDTO configureMaterialDTO) {
        commandService.configureEquipmentInputAndOutput(new EquipmentId(equipmentId),
                configureMaterialDTO.getMaterialInputs(),
                configureMaterialDTO.getMaterialOutputs());
    }

    @PostMapping("/equipment/remove_material_input_output/{equipmentId}")
    public void removeEquipmentInputAndOutput(@PathVariable String equipmentId,
                                              @RequestBody ConfigureMaterialDTO configureMaterialDTO
    ) {
        commandService.removeEquipmentInputAndOutput(new EquipmentId(equipmentId),
                configureMaterialDTO.getMaterialInputs(), configureMaterialDTO.getMaterialOutputs());
    }

    @GetMapping("/equipment/get_equipment_input_output/{equipmentId}")
    public EquipmentInputOutputDTO getEquipmentInputOutput(@PathVariable String equipmentId) {
        return queryService.getEquipmentInputOutput(new EquipmentId(equipmentId));
    }

    @GetMapping("/equipment/resources_by_input_output/{inputId}/{outputId}")
    public List<EquipmentResourceDTO> queryResourcesByInputAndOutput(@PathVariable String inputId,
                                                                  @PathVariable String outputId){
        return queryService.queryResourcesByInputAndOutput(new MaterialId(inputId),
                new MaterialId(outputId));
    }

    @GetMapping("/equipment/resources_by_input_output_jpql/{inputId}/{outputId}")
    public List<String> queryResourcesByInputAndOutputByJPQL(@PathVariable String inputId,
                                                                     @PathVariable String outputId){
        return queryService.queryResourcesByInputAndOutputByJPQL(new MaterialId(inputId),
                new MaterialId(outputId));
    }

    @GetMapping("/equipment/resources_by_input_output")
    public List<EquipmentResourceDTO> queryResourcesByInputAndOutputIds(@RequestBody ConfigureMaterialDTO configureMaterialDTO){
        return queryService.queryResourcesByInputAndOutputIds(
                configureMaterialDTO.getMaterialInputs().stream().map(v->v.id()).collect(Collectors.toList()),
                configureMaterialDTO.getMaterialOutputs().stream().map(v->v.id()).collect(Collectors.toList()));
    }
}