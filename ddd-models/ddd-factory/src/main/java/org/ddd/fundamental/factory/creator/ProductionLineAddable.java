package org.ddd.fundamental.factory.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.factory.value.WorkStationValueObject;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(1)
public class ProductionLineAddable implements DataAddable {

    @Autowired
    private ProductionLineRepository repository;

    @Autowired
    private RedisStoreManager manager;

    private List<ProductionLine> lines = new ArrayList<>();



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
    @Override
    @Transactional
    public void execute() {
        log.info("ProductionLineAddable execute add all ProductionLines");
        lines = createProductionLine("电路板产线",4,6);
        repository.persistAll(lines);
        List<ProductLineDTO> productLineDTOS = lines.stream()
                .map(v->ProductLineDTO.create(v.id(), v.getLine(),
                        v.getEquipmentIds(),v.getWorkStations().stream()
                                .map(u-> WorkStationDTO.create(u.id(),
                                        u.getWorkStation())).collect(Collectors.toList())
                        )).collect(Collectors.toList());
        manager.storeDataListToCache(productLineDTOS);
        log.info("finish");
    }

    public List<ProductionLine> lines() {
        return new ArrayList<>(lines);
    }
}
