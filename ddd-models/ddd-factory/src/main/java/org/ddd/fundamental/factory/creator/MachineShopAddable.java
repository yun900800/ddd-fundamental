package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(2)
public class MachineShopAddable implements DataAddable {

    @Autowired
    private MachineShopRepository machineShopRepository;

    @Autowired
    private ProductionLineAddable productionLineAddable;

    private List<MachineShop> machineShops = new ArrayList<>();

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
        for (ProductionLine line: productionLineAddable.lines()) {
            MachineShop machineShop = CollectionUtils.random(machineShops);
            machineShop.addLines(line.id());
        }
    }
    @Override
    @Transactional
    public void execute() {
        log.info("MachineShopAddable execute add all machineShops");
        this.machineShops =  createMachineShop();
        addLineRandomToMachineShop();
        machineShopRepository.saveAll(machineShops);
        log.info("finish");
    }
}
