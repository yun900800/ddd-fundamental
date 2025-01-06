package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.enums.EquipmentType;

import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;

import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
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
@Order(2)
public class EquipmentAddable implements DataAddable {

    private final EquipmentCommandService commandService;
    private final RedisStoreManager manager;

    private List<Equipment> equipments = new ArrayList<>();

    @Autowired(required = false)
    public EquipmentAddable(EquipmentCommandService commandService,
                            RedisStoreManager manager){
        this.commandService = commandService;
        this.manager = manager;
    }

    public static List<Equipment> createEquipments(){

        Equipment equipment0 = new Equipment(
                YearModelValue.createThreeShift("三班次年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
        EquipmentResource resource0 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment0.id(), ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试删除","这是一种生产设备,用于测试删除")
        ));
        resource0.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment0.setResource(resource0);
        Equipment equipment1 = new Equipment(
                YearModelValue.createTwoShift("两班次年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1025")
                .info(ChangeableInfo.create("车床二号","这是加工使用的车床,可以加工特质材料"))
                .size(EquipmentSize.create(1200,1500,4000))
                .standard(MaintainStandard.create("需要两个月维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","半年验证一次", true))
                .build()
        );
        EquipmentResource resource1 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment1.id(), ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试删除","这是一种生产设备,用于测试删除")
        ));
        resource1.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment1.setResource(resource1);
        Equipment equipment2 = new Equipment(
                YearModelValue.createTwoShift("两班次年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1026")
                .info(ChangeableInfo.create("机床五号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,2000,1200))
                .standard(MaintainStandard.create("需要半年维修一次",new Date()))
                .personInfo(PersonInfo.create(1,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","八个月验证一次", true))
                .build()
        );
        EquipmentResource resource2 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment2.id(), ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试删除","这是一种生产设备,用于测试删除")
        ));
        resource2.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment2.setResource(resource2);
        Equipment equipment3 = new Equipment(
                YearModelValue.createThreeShift("三班次年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1027")
                .info(ChangeableInfo.create("压铸机5号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,4000,1200))
                .standard(MaintainStandard.create("需要半年维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","一年验证一次", true))
                .build()
        );
        EquipmentResource resource3 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment3.id(), ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试删除","这是一种生产设备,用于测试删除")
        ));
        resource3.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment3.setResource(resource3);
        Equipment equipment4 = new Equipment(
                YearModelValue.createThreeShift("三班次年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1028")
                .info(ChangeableInfo.create("压铸机6号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,4000,1200))
                .standard(MaintainStandard.create("需要一年维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","一年验证一次", true))
                .build()
        );
        EquipmentResource resource4 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment4.id(), ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试删除","这是一种生产设备,用于测试删除")
        ));
        resource4.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment4.setResource(resource4);

        return Arrays.asList(equipment0,equipment1,equipment2,equipment3,equipment4);
    }

    public List<Equipment> getEquipments() {
        return new ArrayList<>(equipments);
    }

    @Override
    @Transactional
    public void execute() {
        log.info("store all Equipments to db start");
        this.equipments = createEquipments();
        this.commandService.saveAllEquipment(equipments);
        log.info("store all Equipments to db finished");
        List<EquipmentDTO> toolingDTOS = EquipmentConverter.entityToDTO(equipments);
        manager.storeDataListToCache(toolingDTOS);
    }
}
