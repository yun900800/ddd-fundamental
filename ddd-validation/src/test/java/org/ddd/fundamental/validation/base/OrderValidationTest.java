package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.impl.EmbeddedObjectRule;
import org.ddd.fundamental.validation.rule.impl.LE;
import org.ddd.fundamental.validation.rule.impl.ObjectNotNullRule;
import org.ddd.fundamental.validation.rule.impl.RuleManager;
import org.junit.Assert;
import org.junit.Test;

public class OrderValidationTest {

    @Test
    public void testOrderFailedValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",contact);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), false);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),1);
    }

    @Test
    public void testOrderSuccessValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",contact,80);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), true);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),0);
    }

    @Test
    public void testOrderAndValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",contact,60);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), true);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),0);
    }

    public static class Order extends DomainModel<Long>{
        private String name;
        private Contact contact;

        private int amount;

        public Order(String name, Contact contact) {
            this.name = name;
            this.contact = contact;
            this.amount = 0;
        }

        public Order(String name, Contact contact, int amount) {
            this(name,contact);
            this.amount = amount;
        }

        @Override
        protected void addRule(RuleManager ruleManager) {
            super.addRule(ruleManager);
            ruleManager.addRule(new ObjectNotNullRule("contact", this.contact));
            ruleManager.addRule(new EmbeddedObjectRule("contact", this.contact));
            ruleManager.addRule(new ObjectNotNullRule("name", this.name)
                    .and(new LE("amount", this.amount,50)));
        }
    }

    public static class Contact extends DomainModel<Long>{

    }
}
