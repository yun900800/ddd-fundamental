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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/bom/add_node_to_bom/{productId}")
    public void addNodeToBom(@PathVariable String productId,
                             @RequestBody MaterialIdNode<ProductStructureNode> node){
        bomCommandService.addNodeToBom(new MaterialId(productId),node);
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
