package org.ddd.fundamental.repository.core.repository;

import org.ddd.fundamental.repository.core.EntityModel;
import org.junit.Test;

public class RepositoryTest {

    @Test
    public void testRepositoryInterface() {
        Order order = new Order(1000l);
        OrderRepository repository = null;
        //repository.add(order);
    }
}

interface OrderRepository extends Repository<Long,Order> {

}

class Order extends EntityModel<Long> {

    public Order(Long aLong) {
        super(aLong);
    }
}
