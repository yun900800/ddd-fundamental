package org.ddd.fundamental.bom.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
public class BomQueryService {
    private final ProductStructureDataRepository structureDataRepository;

    private final MaterialClient materialClient;

    public BomQueryService(ProductStructureDataRepository structureDataRepository,
                             MaterialClient materialClient){
        this.structureDataRepository = structureDataRepository;
        this.materialClient = materialClient;
    }

    public List<MaterialDTO> materialsByMaterialType(MaterialType materialType){
        return materialClient.materialsByMaterialType(materialType);
    }

    public List<MaterialDTO> materialsByIds(List<String> ids){
        return materialClient.materialsByIds(ids);
    }

}
