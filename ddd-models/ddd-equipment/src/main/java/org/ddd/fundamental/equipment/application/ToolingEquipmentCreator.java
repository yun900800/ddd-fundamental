package org.ddd.fundamental.equipment.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
                DateTimeUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange range1 = new DateRange(
                DateTimeUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        DateRange range2 = new DateRange(
                DateTimeUtils.strToDate("2024-09-24 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-25 11:48:12","yyyy-MM-dd HH:mm:ss"),"人员操作失误");
        DateRange range3 = new DateRange(
                DateTimeUtils.strToDate("2024-09-23 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-24 11:48:12","yyyy-MM-dd HH:mm:ss"),"机器技术故障");
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
