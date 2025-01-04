package org.ddd.fundamental.workorder.application.mapper;

import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productOrder", target = "productOrder")
    void updateProductFromDto(ProductOrderDTO dto, @MappingTarget ProductOrder entity);
}
