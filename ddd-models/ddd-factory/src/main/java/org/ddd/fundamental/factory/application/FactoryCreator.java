package org.ddd.fundamental.factory.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.factory.value.WorkStationValueObject;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class FactoryCreator {

    @Autowired
    private ProductionLineRepository lineRepository;

    @Autowired
    private MachineShopRepository machineShopRepository;

    private List<ProductionLine> lines = new ArrayList<>();

    private List<MachineShop> machineShops = new ArrayList<>();

    private static List<ProductionLine> createProductionLine(String lineName, int size, int count){
        List<ProductionLine> productionLines = new ArrayList<>();
        for (int i = 0 ; i < size; i++) {
            ProductionLine productionLine = new ProductionLine(new ProductionLineValue(
                    ChangeableInfo.create(lineName + (i+1), "这是一个生产"+lineName+"的产线")
            ));
            int number = new Random().nextInt(count);
            number += count;
            for (int j = 0 ; j < number; j++)  {
                productionLine.addWorkStation(new WorkStation(
                        new WorkStationValueObject(ChangeableInfo.create(
                                lineName + (i+1) + "工位"+ (j+1), "工位"+ (j+1)+"需要一点点技术哈"
                        ))
                ));
            }
            productionLines.add(productionLine);
        }
        return productionLines;
    }

    private static List<MachineShop> createMachineShop() {
        MachineShop machineShop1 = new MachineShop(new MachineShopValueObject(
                ChangeableInfo.create("电路板三车间", "这是一个制作电路板的车间")
        ));

        MachineShop machineShop2 = new MachineShop(new MachineShopValueObject(
                ChangeableInfo.create("电路板四车间", "这也是一个制作电路板的车间")
        ));
        return Arrays.asList(machineShop1,machineShop2);
    }

    private void addLineRandomToMachineShop(){
        for (ProductionLine line:lines) {
            MachineShop machineShop = CollectionUtils.random(machineShops);
            machineShop.addLines(line.id());
        }
    }

    @PostConstruct
    public void  init(){
        lineRepository.deleteAll();
        log.info("删除产线和工位数据成功");
        lines = createProductionLine("电路板产线",4,6);
        lineRepository.saveAll(lines);
        log.info("创建产线和工位数据成功");

        machineShopRepository.deleteAll();
        log.info("删除车间数据成功");
        this.machineShops =  createMachineShop();
        addLineRandomToMachineShop();
        machineShopRepository.saveAll(machineShops);
        log.info("创建车间并且添加产线到车间成功");
    }
}
