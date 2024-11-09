package org.ddd.fundamental.material.application;

import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MaterialApplication {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialCreator creator;

    public List<MaterialDTO> materials() {
        List<Material> materials = creator.getMaterialList();
        if (null == materials || CollectionUtils.isEmpty(materials)) {
            materials =  materialRepository.findAll();
        }
        return materials.stream()
                .map(v->new MaterialDTO(v.getMaterialMaster(),v.id()))
                .collect(Collectors.toList());

    }


}
