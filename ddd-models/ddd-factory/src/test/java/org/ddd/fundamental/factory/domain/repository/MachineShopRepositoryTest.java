package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.FactoryAppTest;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MachineShopRepositoryTest extends FactoryAppTest {

    @Autowired
    private MachineShopRepository repository;

    @Autowired
    private ProductionLineRepository lineRepository;

    @Test
    public void createMachineShop(){
        MachineShop machineShop = new MachineShop(new MachineShopValueObject(
                ChangeableInfo.create("电路板三车间", "这是一个制作电路板的车间")
        ));
        repository.save(machineShop);
    }

    @Test
    public void addProductionLineToMachineShop(){
        MachineShop machineShop = repository.findById(new MachineShopId("640a54e5-c0b8-49e5-8f66-7d6f3f8e632f"))
                .get();
        List<ProductionLine> lines = lineRepository.findAll();
        for (ProductionLine line: lines) {
            machineShop.addLines(line.id());
        }
        repository.save(machineShop);
    }

}
