package org.ddd.fundamental.material.application;

import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体和DTO之间的转换器
 */
public class MaterialConverter {
    public static List<MaterialDTO> entityToDTO(List<Material> materials){
        if (CollectionUtils.isEmpty(materials)) {
            return new ArrayList<>();
        }
        return materials.stream()
                .map(v->new MaterialDTO(v.getMaterialMaster(),v.id(),v.getMaterialType()))
                .collect(Collectors.toList());
    }
}
