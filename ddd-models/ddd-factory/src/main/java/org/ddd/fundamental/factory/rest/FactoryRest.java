package org.ddd.fundamental.factory.rest;

import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.application.FactoryApplication;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FactoryRest {

    @Autowired
    private FactoryApplication application;

    @RequestMapping("/factory/machine-shops")
    public List<MachineShopDTO> machineShops() {
        return application.machineShops();
    }

    @RequestMapping("/factory/machine-shopsByIds")
    public List<MachineShopDTO> machineShopsByIds(@RequestBody List<String> ids){
        return application.machineShopsByIds(ids.stream().map(v->new MachineShopId(v))
                .collect(Collectors.toList()));
    }

    @RequestMapping("/factory/product-lines")
    public List<ProductLineDTO> productLines() {
        return application.productLines();
    }

    @RequestMapping("/factory/product-linesByIds")
    public List<ProductLineDTO> productLinesByIds(@RequestBody List<String> ids){
        return application.productLinesByIds(ids.stream().map(v->new ProductionLineId(v))
                .collect(Collectors.toList()));
    }

}
