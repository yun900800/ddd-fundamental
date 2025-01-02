package org.ddd.fundamental.workorder.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workorder.application.WorkOrderConverter;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.domain.model.ProductOrder_;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    public List<ProductOrderDTO> productOrders(){
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        if (CollectionUtils.isEmpty(productOrders)) {
            return new ArrayList<>();
        }
        return WorkOrderConverter.entityToDTO(productOrders);
    }

    public ProductOrder findProductOrderById(OrderId orderId){
        ProductOrder productOrder = productOrderRepository.findById(orderId).orElse(null);
        if (null == productOrder) {
            String msg = "orderId : %s 对应的productOrder不存在";
            throw new RuntimeException(String.format(msg,orderId.toUUID()));
        }
        return productOrder;
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

    public Page<ProductOrderDTO> fetchProductOrder(int pageNumber,
                                                   int pageSize){
        long total = productOrderRepository.fetchProductOrderCount();
        List<ProductOrder> productOrders =  productOrderRepository.fetchProductOrderList(pageNumber,pageSize);
        List<ProductOrderDTO> productOrderDTOS =  WorkOrderConverter.entityToDTO(productOrders);
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        return new PageImpl<>(productOrderDTOS, pageable,total);
    }

    public Page<ProductOrderDTO> fetchProductOrderByIds(int pageNumber,
                                                        int pageSize){
        TwoTuple<Long,List<ProductOrder>> twoTuple = productOrderRepository.fetchProductOrderByIds(pageNumber,pageSize);
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        List<ProductOrder> productOrders = twoTuple.second;
        return new PageImpl<>(WorkOrderConverter.entityToDTO(productOrders), pageable,twoTuple.first);
    }

    public Page<ProductOrderDTO> fetchProductOrderByAPI(int pageNumber,
                                                     int pageSize){
        TwoTuple<Long,List<ProductOrder>> twoTuple = productOrderRepository.fetchProductOrderByAPI(pageNumber,pageSize);
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        List<ProductOrder> productOrders = twoTuple.second;
        return new PageImpl<>(WorkOrderConverter.entityToDTO(productOrders), pageable,twoTuple.first);
    }

}
