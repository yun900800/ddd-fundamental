package org.ddd.fundamental.equipment.helper;

import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.enums.EquipmentPositionType;
import org.ddd.fundamental.utils.DateTimeUtils;
import org.ddd.fundamental.utils.RandomNumberUtil;

import java.util.Arrays;
import java.util.List;

public final class EquipmentHelper {

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

    public static List<YearModelValue> yearModelValueList(){
        return Arrays.asList(
                YearModelValue.createThreeShift("电路板三班制"),
                YearModelValue.createTwoShift("电路板两班制"),
                YearModelValue.createThreeShift("电路板三班制",false),
                YearModelValue.createTwoShift("电路板两班制",false)
        );
    }

    public static String assetNo(String prefix){
        return prefix + "_" +DateTimeUtils.getTodayChar14()+"_"+ RandomNumberUtil.createRandomNumber(6);
    }

    public static List<String> maintainRules(){
        return Arrays.asList(
                "需要专业的设备工人维护",
                "只需要为设备添加润滑油",
                "设备很皮实,每个月维护一次",
                "设备的维护需要审核才能进行",
                "设备每年维护一次",
                "设备用其他设备进行维护"
        );
    }

    public static List<Integer> personCounts(){
        return Arrays.asList(
             1,2,3,4,5,6,7,8
        );
    }

    public static List<String> checkPlans(){
        return Arrays.asList(
            "每年验证一次",
                "半年验证一次",
                "八个月验证一次",
                "一年验证一次",
                "一年半验证一次"
        );
    }

    public static List<String> checkStatus(){
        return Arrays.asList(
                "检查合格",
                "检查不合格",
                "部分合格,不分不合格",
                "严重问题,重新检查",
                "勉强合格通过"
        );
    }

    public static List<String> equipmentNames(){
        return Arrays.asList(
                "车床一号",
                "车床四号",
                "车床七号",
                "压铸设备七号",
                "压铸设备五号",
                "车床3号",
                "车床11号",
                "车床25号",
                "压铸设备6号",
                "压铸设备8号"
        );
    }

    public static List<Double> randomDoubles(){
        return Arrays.asList(100.0,
                203.0,580.5,
                209.0,510.0,
                201.0,500.5,
                200.0,500.0);
    }

    public static List<EquipmentPositionType> positionTypes(){
        return Arrays.asList(
                EquipmentPositionType.values()
        );
    }
}
