package org.ddd.fundamental.workorder.domain.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.domain.repository.CustomProductOrderRepository;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Slf4j
public class ProductOrderRepositoryImpl implements CustomProductOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long fetchProductOrderCount() {
        Query queryTotal = entityManager.createQuery
                ("Select count(f.id) from ProductOrder f");
        long countResult = (long)queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    public List<ProductOrder> fetchProductOrderList(int pageNumber, int pageSize) {
        //这个方法不能查询出总的数据,因此需要在service中拼装出分页数据,
        //这是这个问题的局限性
        Query query = entityManager.createQuery("From ProductOrder");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List<ProductOrder> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public TwoTuple<Long, List<ProductOrder>> fetchProductOrderByAPI(int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder
                .createQuery(Long.class);
        countQuery.select(criteriaBuilder
                .count(countQuery.from(ProductOrder.class)));
        Long count = entityManager.createQuery(countQuery)
                .getSingleResult();

        CriteriaQuery<ProductOrder> criteriaQuery = criteriaBuilder
                .createQuery(ProductOrder.class);
        Root<ProductOrder> from = criteriaQuery.from(ProductOrder.class);
        CriteriaQuery<ProductOrder> select = criteriaQuery.select(from);

        TypedQuery<ProductOrder> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult((pageNumber-1) * pageSize);
        typedQuery.setMaxResults(pageSize);
        List<ProductOrder> productOrders = typedQuery.getResultList();
        return Tuple.tuple(count, productOrders);
    }

    @Override
    public TwoTuple<Long, List<ProductOrder>> fetchProductOrderByIds(int pageNumber, int pageSize) {
        Query queryForIds = entityManager.createQuery(
                "Select f.id from ProductOrder f order by f.auditable.createTime");
        List<OrderId> ids = queryForIds.getResultList();
        Query query = entityManager.createQuery(
                "Select f from ProductOrder f where f.id in :ids");
        query.setParameter("ids", ids.subList(pageNumber-1,pageSize));
        List<ProductOrder> productOrders = query.getResultList();
        return Tuple.tuple((long)ids.size(), productOrders);
    }
}
