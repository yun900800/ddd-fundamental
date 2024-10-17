package org.ddd.fundamental.core;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.lang.NonNull;

public class DomainObjectIdTest {
    @Test
    public void testDomainObjectIdCreate(){
        String id = DomainObjectId.randomId(String.class);
        Assert.assertTrue(id.length()==36);
    }

    @Test
    public void testDomainObjectIdClass(){
        OrderItemId id = DomainObjectId.randomId(OrderItemId.class);
        Assert.assertTrue(id.toUUID().length()==36);
    }
}

class OrderItemId extends DomainObjectId{
    public OrderItemId(@NonNull String id){
        super(id);
    }
}
