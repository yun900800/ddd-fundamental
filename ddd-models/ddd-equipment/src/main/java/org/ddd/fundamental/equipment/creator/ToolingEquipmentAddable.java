package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.enums.ToolingType;
import org.ddd.fundamental.equipment.value.MaintainStandard;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@Order(3)
public class ToolingEquipmentAddable implements DataAddable {

    private final EquipmentCommandService commandService;

    private final RedisStoreManager manager;

    private List<ToolingEquipment> toolingEquipments = new ArrayList<>();

    private final EquipmentAddable equipmentAddable;

    @Autowired(required = false)
    public ToolingEquipmentAddable(EquipmentCommandService commandService,
                                     RedisStoreManager manager,
                                   EquipmentAddable equipmentAddable){
        this.commandService = commandService;
        this.manager = manager;
        this.equipmentAddable = equipmentAddable;
    }

    public Equipment random(){
        return org.ddd.fundamental.utils.CollectionUtils.random(equipmentAddable.getEquipments());
    }

    public static List<ToolingEquipment> createToolingList(){
        ToolingEquipment toolingEquipment0 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("加工中心","这是一个加工设备设备"),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.SB,"VMC-850"),
                "ASSET_SB_JGZX_1","SB_JGZX_1");
        ToolingEquipment toolingEquipment1 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"精镗孔镗刀体"),
                "ASSET_JJ_DT_1","JJ_DT_1");

        ToolingEquipment toolingEquipment2 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"铣刀盘"),
                "ASSET_JJ_DT_2","JJ_DT_2");
        ToolingEquipment toolingEquipment3 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"ER刀体"),
                "ASSET_JJ_DT_3","JJ_DT_3");

        ToolingEquipment toolingEquipment4 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("铣大面",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XDM_1","GZ_XDM_1");

        ToolingEquipment toolingEquipment5 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("铣顶面转",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XDMZ_1","GZ_XDMZ_1");
        ToolingEquipment toolingEquipment6 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("铣平面",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XPM_1","GZ_XPM_1");
        ToolingEquipment toolingEquipment7 = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("铣侧面转2空",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XCMZ_1","GZ_XCMZ_1");
        return Arrays.asList(toolingEquipment0,toolingEquipment1,
                toolingEquipment2, toolingEquipment3,toolingEquipment4,
                toolingEquipment5,toolingEquipment6,toolingEquipment7);
    }

    public List<ToolingEquipment> getToolingEquipments() {
        return new ArrayList<>(toolingEquipments);
    }

    public void randomSetMasterEquipment() {
        toolingEquipments.stream().forEach(v->v.setEquipment(this.random()));
    }

    @Override
    @Transactional
    public void execute() {
        log.info("store all ToolingEquipments to db start");
        this.toolingEquipments = createToolingList();
        randomSetMasterEquipment();
        this.commandService.saveAllTooling(toolingEquipments);
        log.info("store all ToolingEquipments to db finished");
        List<ToolingDTO> toolingDTOS = EquipmentConverter.entityToToolingDTO(toolingEquipments);
        manager.storeDataListToCache(toolingDTOS);
    }
}
