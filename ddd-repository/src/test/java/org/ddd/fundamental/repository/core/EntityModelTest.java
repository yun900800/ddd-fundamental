package org.ddd.fundamental.repository.core;

import org.junit.Assert;
import org.junit.Test;

public class EntityModelTest {

    @Test
    public void testEntityModelCreate() {
        Order order = new Order(1000L,"test");
        Assert.assertEquals(1000L, order.getId(),0);
        Assert.assertEquals("test", order.getName());
    }

    private static class Order extends EntityModel<Long>{

        private String name;

        private Contact contact;

        public Order(Long aLong, String name) {
            super(aLong);
            this.name = name;
        }

        public Order(Long id, String name, Contact contact) {
            super(id);
            this.name = name;
            this.contact = contact;
        }

        public String getName() {
            return name;
        }

        public Contact getContact() {
            return contact;
        }
    }

    private static class Contact {
        private String email;
        private String name;

        public Contact(String email, String name) {
            this.email = email;
            this.name = name;
        }
    }

    private static class OrderFactory extends Order {

        private OrderFactory(Long id, String name, Contact contact) {
            super(id, name, contact);
        }


        public static Order create(OrderVO orderInfo) throws OrderCreationException {
            if (orderInfo == null) {
                throw new OrderCreationException();
            }
            Contact contact = new Contact(orderInfo.getEmail(), orderInfo.getName());

            return new Order(0L, orderInfo.getName(), contact);
        }
    }

    private static class OrderVO {

        private String email;
        private String name;

        public OrderVO(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }

    private static class OrderCreationException extends RuntimeException{

    }
}


