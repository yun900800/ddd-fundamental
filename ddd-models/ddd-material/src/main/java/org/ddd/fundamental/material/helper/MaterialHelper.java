package org.ddd.fundamental.material.helper;

import org.ddd.fundamental.material.domain.enums.MaterialLevel;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.utils.DateTimeUtils;
import org.ddd.fundamental.utils.RandomNumberUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MaterialHelper {

    public static List<String> units(){
        return Arrays.asList("个","瓶","箱","颗","台","桶");
    }

    public static List<String> materialTypes(){
        return Arrays.asList("rawMaterial","workInProgress","production");
    }

    public static List<MaterialLevel> materialLevels(){
        return Arrays.asList(MaterialLevel.values());
    }

    public static List<Integer> numbers(){
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,15,18,30);
    }

    public static String generateCode(String spec){
        return spec + "_" +DateTimeUtils.getTodayChar14()+"_"+RandomNumberUtil.createRandomNumber(6);
    }

    public static String generateSerialNo(String identifier,int randomLength) {
        return DateTimeUtils.getTodayChar17() + identifier
                + RandomNumberUtil.createRandomNumber(randomLength);
    }

    public static Map<MaterialType, List<String>> createMaterial(){
        Map<MaterialType,List<String>> map = new HashMap<>();
        map.put(MaterialType.RAW_MATERIAL, Arrays.asList(
                "螺纹钢","锡膏","测试仪器","螺钉","纸张"
        ));
        map.put(MaterialType.WORKING_IN_PROGRESS,Arrays.asList(
                "工序1的在制品","工序5的在制品","工序3的在制品","工序8的在制品","工序9的在制品"
        ));
        map.put(MaterialType.PRODUCTION,Arrays.asList(
                "鼠标","主板","酒瓶塞子","电脑","玩具"
        ));
        return map;
    }

}
