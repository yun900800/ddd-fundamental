package org.ddd.fundamental.bom.rest;

import org.ddd.fundamental.bom.application.command.BomCommandService;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BomRest {

    private final BomCommandService bomCommandService;

    private final BomQueryService bomQueryService;

    @Autowired
    public BomRest(BomCommandService bomCommandService,
                   BomQueryService bomQueryService){
        this.bomCommandService = bomCommandService;
        this.bomQueryService = bomQueryService;
    }

    @PostMapping("/bom/create_product_bom/{productId}/{spareSize}/{rawSize}")
    public void createProductBom(@PathVariable String productId,
                                 @PathVariable int spareSize,
                                 @PathVariable int rawSize){
        bomCommandService.createProductBom(new MaterialId(productId),
                spareSize,rawSize);
    }

}
