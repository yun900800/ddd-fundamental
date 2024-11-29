package org.ddd.fundamental.factory.rest;

import org.ddd.fundamental.factory.application.FactoryApplication;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FactoryRest {

    @Autowired
    private FactoryApplication application;

    @RequestMapping("/factory/machine-shops")
    public List<MachineShopDTO> machineShops() {
        return application.machineShops();
    }

    @RequestMapping("/factory/product-lines")
    public List<ProductLineDTO> productLines() {
        return application.productLines();
    }
}
