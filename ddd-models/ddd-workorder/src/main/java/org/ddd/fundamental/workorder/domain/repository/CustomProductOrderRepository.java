package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;

import java.util.List;

public interface CustomProductOrderRepository {

    List<ProductOrder> fetchProductOrderList(int pageNumber, int pageSize);

    long fetchProductOrderCount();

    TwoTuple<Long,List<ProductOrder>> fetchProductOrderByIds(int pageNumber,int pageSize);

    TwoTuple<Long,List<ProductOrder>> fetchProductOrderByAPI(int pageNumber,int pageSize);
}
