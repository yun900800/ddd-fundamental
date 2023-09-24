package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderRepository")
public class OrderRepositoryImpl extends RepositoryBase<Long, Order>
        implements OrderRepository {

    @Override
    public void persistNewCreated(Order oder) throws PersistenceException {
        if (oder == null) {
            throw new PersistenceException();
        }
        try {
//            OrderDataEntity orderDataEntity = this.ofOrderData(oder);
//            List<OrderEntryDataEntity> orderEntryDataEntities = this.ofOrderEntryData(oder);
//            this.orderMapper.save(orderDataEntity);
//            this.orderEntryMapper.save(orderEntryDataEntities);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void persistDeleted(Order order) throws PersistenceException {
        if (order == null) {
            throw new PersistenceException();
        }
        try {
//            OrderDataEntity orderDataEntity = this.ofOrderData(oder);
//            List<OrderEntryDataEntity> orderEntryDataEntities = this.ofOrderEntryData(oder);
//            this.orderMapper.delete(orderDataEntity);
//            this.orderEntryMapper.delete(orderEntryDataEntities);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void persistChanged(Order order) throws PersistenceException {

    }

    @Override
    public Order findBy(Long id) throws RuntimeException {
        return null;
    }
}
