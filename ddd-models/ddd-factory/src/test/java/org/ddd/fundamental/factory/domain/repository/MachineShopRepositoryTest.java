package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.FactoryAppTest;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.ddd.fundamental.utils.CollectionUtils;
import org.junit.Assert;
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
        repository.persist(machineShop);
    }

    @Test
    public void addProductionLineToMachineShop(){
        List<MachineShop> machineShops = repository.findAll();
        MachineShop machineShop = CollectionUtils.random(machineShops);
        List<ProductionLine> lines = lineRepository.findAll();
        for (ProductionLine line: lines) {
            machineShop.addLines(line.id());
        }
        repository.merge(machineShop);
    }

    @Test
    public void removeProductionLineFromMachineShop(){
        List<MachineShop> machineShops = repository.findAll();
        MachineShop machineShop = CollectionUtils.random(machineShops);
        List<ProductionLine> lines = lineRepository.findAll();
        for (ProductionLine line: lines) {
            machineShop.addLines(line.id());
        }
        repository.merge(machineShop);
        MachineShopId id = machineShop.id();
        MachineShop query = repository.findById(id).orElse(null);
        query.removeLines(query.getLines().get(0));
        repository.merge(query);


    }

    @Test
    public void changeMachineShopInfo() {
        MachineShop machineShop = new MachineShop(new MachineShopValueObject(
                ChangeableInfo.create("电路板三车间", "这是一个制作电路板的车间")
        ));
        repository.persist(machineShop);
        MachineShopId id = machineShop.id();
        MachineShop query = repository.findById(id).orElse(null);
        query.changeName("测试车间修改").changeDesc("描述修改").enableUse();
        repository.merge(query);
        MachineShop result = repository.findById(id).orElse(null);
        Assert.assertEquals(result.getMachineShop().name(),"测试车间修改");
        Assert.assertEquals(result.getMachineShop().desc(),"描述修改");
        Assert.assertEquals(result.getMachineShop().isUse(),true);
    }

}
