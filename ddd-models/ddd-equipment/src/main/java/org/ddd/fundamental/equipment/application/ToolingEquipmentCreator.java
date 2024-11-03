package org.ddd.fundamental.equipment.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentType;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.enums.ToolingType;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ToolingEquipmentCreator {

    @Autowired
    private ToolingEquipmentRepository repository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private RPAccountRepository accountRepository;

    private List<ToolingEquipment> toolingEquipments = new ArrayList<>();



    private List<Equipment> equipments = new ArrayList<>();

    private List<DateRange> dateRanges = new ArrayList<>();

    private List<RPAccount> rpAccounts = new ArrayList<>();

    public static List<RPAccount> createRpaAccount(){
        RPAccount account1 = new RPAccount(
                RPAccountValue.create(1,"SUT","二级使用时间")
        );
        RPAccount account2 = new RPAccount(
                RPAccountValue.create(2,"DCI","干扰性中断,也叫作技术性停机")
        );
        RPAccount account3 = new RPAccount(
                RPAccountValue.create(3,"LCI","物流性中断,也叫作组织性停机")
        );
        RPAccount account4 = new RPAccount(
                RPAccountValue.create(4,"SCI","人员性中断")
        );
        RPAccount account5 = new RPAccount(
                RPAccountValue.create(5,"IMN","非计划设备闲置")
        );
        RPAccount account6 = new RPAccount(
                RPAccountValue.create(6,"IMS","计划的设备闲置")
        );
        RPAccount account7 = new RPAccount(
                RPAccountValue.create(7,"SET","调试")
        );
        RPAccount account8 = new RPAccount(
                RPAccountValue.create(8,"STA","启动")
        );
        RPAccount account9 = new RPAccount(
                RPAccountValue.create(9,"U8","可自定义(如试制生产或类似的时间)")
        );
        RPAccount account10 = new RPAccount(
                RPAccountValue.create(10,"U9","可自定义")
        );
        RPAccount account11 = new RPAccount(
                RPAccountValue.create(11,"MUT","主要利用时间,即生产时间")
        );
        RPAccount account12 = new RPAccount(
                RPAccountValue.create(12,"BKS","休息时间,例如停工，休息，即不计入生产开工的其他时间")
        );
        return Arrays.asList(account1,account2,account3,account4
            ,account5,account6,account7,account8,
                account9,account10,account11,account12);
    }

    public static List<DateRange> createDateRanges(){
        DateRange range0 = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange range1 = new DateRange(
                DateUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        DateRange range2 = new DateRange(
                DateUtils.strToDate("2024-09-24 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-25 11:48:12","yyyy-MM-dd HH:mm:ss"),"人员操作失误");
        DateRange range3 = new DateRange(
                DateUtils.strToDate("2024-09-23 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-24 11:48:12","yyyy-MM-dd HH:mm:ss"),"机器技术故障");
        return Arrays.asList(range0,range1,range2,range3);
    }

    public static List<Equipment> createEquipments(){
        Equipment equipment0 = new Equipment(
                YearModelValueObject.createThreeShift("三班次年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
        Equipment equipment1 = new Equipment(
                YearModelValueObject.createTwoShift("两班次年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1025")
                .info(ChangeableInfo.create("车床二号","这是加工使用的车床,可以加工特质材料"))
                .size(EquipmentSize.create(1200,1500,4000))
                .standard(MaintainStandard.create("需要两个月维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","半年验证一次", true))
                .build()
        );
        Equipment equipment2 = new Equipment(
                YearModelValueObject.createTwoShift("两班次年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1026")
                .info(ChangeableInfo.create("机床五号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,2000,1200))
                .standard(MaintainStandard.create("需要半年维修一次",new Date()))
                .personInfo(PersonInfo.create(1,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","八个月验证一次", true))
                .build()
        );
        Equipment equipment3 = new Equipment(
                YearModelValueObject.createThreeShift("三班次年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1027")
                .info(ChangeableInfo.create("压铸机5号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,4000,1200))
                .standard(MaintainStandard.create("需要半年维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","一年验证一次", true))
                .build()
        );
        Equipment equipment4 = new Equipment(
                YearModelValueObject.createThreeShift("三班次年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1028")
                .info(ChangeableInfo.create("压铸机6号","这是加工使用的机床,主要用来加工特种锡膏"))
                .size(EquipmentSize.create(4000,4000,1200))
                .standard(MaintainStandard.create("需要一年维修一次",new Date()))
                .personInfo(PersonInfo.create(3,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","一年验证一次", true))
                .build()
        );
        return Arrays.asList(equipment0,equipment1,equipment2,equipment3,equipment4);
    }

    public static List<ToolingEquipment> createToolingList(){
        ToolingEquipment toolingEquipment0 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("加工中心","这是一个加工设备设备"),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.SB,"VMC-850"),
                "ASSET_SB_JGZX_1","SB_JGZX_1");
        ToolingEquipment toolingEquipment1 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"精镗孔镗刀体"),
                "ASSET_JJ_DT_1","JJ_DT_1");

        ToolingEquipment toolingEquipment2 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"铣刀盘"),
                "ASSET_JJ_DT_2","JJ_DT_2");
        ToolingEquipment toolingEquipment3 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("刀体",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.JJ,"ER刀体"),
                "ASSET_JJ_DT_3","JJ_DT_3");

        ToolingEquipment toolingEquipment4 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("铣大面",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XDM_1","GZ_XDM_1");

        ToolingEquipment toolingEquipment5 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("铣顶面转",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XDMZ_1","GZ_XDMZ_1");
        ToolingEquipment toolingEquipment6 = new ToolingEquipment(ToolingEquipmentValueObject
                .create(ChangeableInfo.create("铣平面",""),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.GZ,"没有规格型号"),
                "ASSET_GZ_XPM_1","GZ_XPM_1");
        ToolingEquipment toolingEquipment7 = new ToolingEquipment(ToolingEquipmentValueObject
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

    public List<Equipment> getEquipments() {
        return new ArrayList<>(equipments);
    }

    public List<DateRange> getDateRanges() {
        return new ArrayList<>(dateRanges);
    }

    @PostConstruct
    public void init(){
        repository.deleteAll();
        log.info("删除工装设备成功");
        toolingEquipments = createToolingList();
        repository.saveAll(toolingEquipments);
        log.info("创建工装设备成功");

        equipmentRepository.deleteAll();
        log.info("删除主设备成功");
        equipments = createEquipments();
        equipmentRepository.saveAll(equipments);
        log.info("创建主设备成功");

        this.dateRanges = createDateRanges();

        accountRepository.deleteAll();
        log.info("删除RPA账号成功");
        rpAccounts = createRpaAccount();
        accountRepository.saveAll(rpAccounts);
        log.info("创建RPA账号成功");
    }
}
