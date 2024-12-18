package org.ddd.fundamental.bom.rest;

import org.ddd.fundamental.bom.application.command.BomCommandService;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.ddd.fundamental.shared.api.bom.ProductStructureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/bom/all_bom_ids")
    public List<BomIdDTO> allBomIds(){
        return bomQueryService.allBomIds();
    }

    @RequestMapping("/bom/product_structure/{productId}")
    public ProductStructureDTO getProductStructure(@PathVariable String productId){
        return bomQueryService.getProductStructure(new MaterialId(productId));
    }

}
