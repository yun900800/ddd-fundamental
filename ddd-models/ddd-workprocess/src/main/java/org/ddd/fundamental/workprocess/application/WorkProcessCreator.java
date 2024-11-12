package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.value.controller.GapRangeControl;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkOrderControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class WorkProcessCreator {

    private List<WorkProcessTemplate> workProcessList;

    @Autowired
    private WorkProcessTemplateRepository processNewRepository;

    @Autowired
    private CraftsmanShipRepository craftsmanShipRepository;

    public static List<WorkProcessTemplate> createWorkProcessList() {
        List<WorkProcessTemplate> workProcessNews = new ArrayList<>();
        Generators.fill(workProcessNews,()->createWorkProcessNew(),20);
        return workProcessNews;
    }

    private static List<Boolean> trueOrFalse(){
        return Arrays.asList(true,false);
    }

    public static WorkProcessTemplate createWorkProcessNew(){
        WorkProcessTemplateControl control = new WorkProcessTemplateControl.Builder(1,CollectionUtils.random(trueOrFalse()))
                .canSplit(CollectionUtils.random(trueOrFalse())).isAllowedChecked(CollectionUtils.random(trueOrFalse()))
                .gapRangeControl(GapRangeControl.create(CollectionUtils.random(trueOrFalse()), CollectionUtils.random(trueOrFalse())))
                .reportWorkControl(ReportWorkControl.create(CollectionUtils.random(trueOrFalse()),"测试报工规则"))
                .workOrderControl(WorkOrderControl.create(CollectionUtils.random(trueOrFalse()),CollectionUtils.random(trueOrFalse()),CollectionUtils.random(trueOrFalse())))
                .build();
        WorkProcessTemplate workProcessNew = new WorkProcessTemplate(
                CollectionUtils.random(createWorkProcessInfo()),
                CollectionUtils.random(createAuxiliaryWorkTimes()),
                WorkProcessBeat.create(1000,15),
                control
        );
        return workProcessNew;
    }

    public static List<AuxiliaryWorkTime> createAuxiliaryWorkTimes(){
        String[] dates = {
                "2021-10-01","2021-10-02","2021-10-03","2021-10-04",
                "2021-10-05","2021-10-06","2021-10-07","2021-10-08",
                "2021-10-09","2021-10-10","2021-10-11","2021-10-12",
                "2021-10-13","2021-10-14","2021-10-15","2021-10-16"
        };
        return createAuxiliaryWorkTimes(dates);
    }

    public static List<ChangeableInfo> createWorkProcessInfo(){
        String[] processNames = {
                "拉伸工序","弯曲工序","翻边工序","整形工序",
                "落料工序", "冲孔工序", "切角工序","修边工序",
                "翻孔工序", "斜楔冲孔工序",
                "冲压工序","焊装工序","涂装工序", "总装工序"
        };
        return createWorkProcessInfo(processNames);
    }

    public static List<ChangeableInfo> createWorkProcessInfo(String[] processNames){
        List<ChangeableInfo> workProcessInfo = new ArrayList<>();
        for (String processName: processNames) {
            Generators.fill(workProcessInfo,()->ChangeableInfo.create(processName,
                            "这是对"+processName+"的描述,请仔细查阅"),
                    1);
        }
        return workProcessInfo;
    }

    public static List<AuxiliaryWorkTime> createAuxiliaryWorkTimes(String[] dates){
        List<AuxiliaryWorkTime> list = new ArrayList<>();
        for (String date: dates) {
            Generators.fill(list,()->createAuxiliaryWorkTime(date),
                    1);
        }
        return list;
    }

    private static AuxiliaryWorkTime createAuxiliaryWorkTime(String date){
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate(date+" 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange offlineTimeRange = new DateRange(
                DateUtils.strToDate(date+" 14:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        DateRange checkTimeRange = new DateRange(
                DateUtils.strToDate(date+" 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序检查时间");
        return AuxiliaryWorkTime.create(setTimeRange,offlineTimeRange,checkTimeRange);
    }
    @PostConstruct
    public void init(){
        processNewRepository.deleteAll();
        log.info("删除所有工序成功");
        workProcessList = createWorkProcessList();
        processNewRepository.saveAll(workProcessList);
        log.info("创建所有工序成功");

        log.info("删除所有工序成功");
        craftsmanShipRepository.deleteAll();
    }

}
