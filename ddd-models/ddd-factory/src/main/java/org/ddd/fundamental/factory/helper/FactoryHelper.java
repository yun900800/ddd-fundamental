package org.ddd.fundamental.factory.helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class FactoryHelper {

    public static List<String> productLineNames(){
        return Arrays.asList(
                "电路板产线1",
                "电路板产线3",
                "电路板产线4",
                "电路板产线8",
                "电路板产线6",
                "电路板产线8",
                "电路板产线2"
        );
    }

    public static List<String> workStationNames(){
        return Arrays.asList(
                "工位1",
                "工位2",
                "工位3",
                "工位4",
                "工位5",
                "工位6",
                "工位7"
        );
    }

    public static List<String> machineShopNames(){
        return Arrays.asList(
                "铸造3车间",
                "模切4车间",
                "加工5车间",
                "加工2车间",
                "上料6车间",
                "质检7车间",
                "铸造4车间",
                "铸造8车间"
        );
    }

    public static void simulateCostCall(){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e){

        }

    }
}
