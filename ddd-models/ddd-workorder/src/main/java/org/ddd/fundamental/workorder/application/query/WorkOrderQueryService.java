package org.ddd.fundamental.workorder.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.Auditable_;
import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.workorder.application.WorkOrderConverter;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.domain.model.ProductOrder_;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class WorkOrderQueryService {

    private final ProductOrderRepository productOrderRepository;

    private final WorkOrderRepository workOrderRepository;

    @Autowired(required = false)
    public WorkOrderQueryService(ProductOrderRepository productOrderRepository,
                                 WorkOrderRepository workOrderRepository){
        this.productOrderRepository = productOrderRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public Page<ProductOrderDTO> findAllByProductName(String productName,
                                                   int pageNumber,
                                                   int pageSize){
        //下面的代码需要使用JPA2.7
        //Sort sort = JpaSort.of(JpaSort.Direction.ASC, JpaSort.path(ProductOrder_.auditable).dot(Auditable_.createTime));
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(ProductOrder_.AUDITABLE+".createTime").descending());
        Page<ProductOrder> pageData =  productOrderRepository.findAllByProductOrder_ProductName(
                productName, pageable
        );
        List<ProductOrderDTO> productOrderDTOS = WorkOrderConverter.entityToDTO(
                pageData.toList()
        );
        return new PageImpl<>(productOrderDTOS, pageable,pageData.getTotalElements());
    }
}
