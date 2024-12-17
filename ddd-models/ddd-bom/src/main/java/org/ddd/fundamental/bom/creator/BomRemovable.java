package org.ddd.fundamental.bom.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.creator.DataRemovable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class BomRemovable implements DataRemovable {


    private final ProductStructureDataRepository repository;

    @Autowired
    public BomRemovable(ProductStructureDataRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("start deleteAll ProductStructureData ");
        this.repository.deleteProductStructures();
        log.info("finished deleteAll ProductStructureData ");
    }
}
