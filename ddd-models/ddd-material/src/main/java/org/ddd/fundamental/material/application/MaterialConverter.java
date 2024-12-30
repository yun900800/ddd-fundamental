package org.ddd.fundamental.material.application;

import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.enums.MaterialInputOutputType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.EnumsUtils;
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
                .map(MaterialConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    public static MaterialDTO entityToDTO(Material v){
        return MaterialDTO.create(v.getMaterialMaster(),v.id(),v.getMaterialType(),
                EnumsUtils.findEnumValue(MaterialInputOutputType.class,
                        v.getMaterialRequiredProps().get("inputOrOutputType"))
                );
    }

    public static List<ProductEventCreated> entityToEvent(List<Material> materials){
        if (CollectionUtils.isEmpty(materials)) {
            return new ArrayList<>();
        }
        return materials.stream()
                .map(MaterialConverter::entityToEvent)
                .collect(Collectors.toList());
    }

    public static ProductEventCreated entityToEvent(Material v){
        return ProductEventCreated.create(DomainEventType.MATERIAL,
                v.getMaterialMaster(), v.getMaterialControlProps().getMaterialType(),
                v.id());
    }
}
