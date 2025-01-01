package org.ddd.fundamental.workorder.application;

import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class WorkOrderConverter {

    public static ProductOrderDTO entityToDTO(ProductOrder productOrder){
        return ProductOrderDTO.create(
                productOrder.getProductOrder(),productOrder.id()
        );
    }

    public static List<ProductOrderDTO> entityToDTO(List<ProductOrder> productOrders){
        if (CollectionUtils.isEmpty(productOrders)){
            return new ArrayList<>();
        }
        return productOrders.stream().map(WorkOrderConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
