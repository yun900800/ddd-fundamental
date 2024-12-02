package org.ddd.fundamental.equipment.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.domain.model.*;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.enums.ToolingType;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
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


    private List<DateRange> dateRanges = new ArrayList<>();


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

    public List<DateRange> getDateRanges() {
        return new ArrayList<>(dateRanges);
    }

    //@PostConstruct
    public void init(){

        this.dateRanges = createDateRanges();
    }
}
