package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.LE;
import org.ddd.fundamental.validation.rule.ObjectNotNullRule;
import org.ddd.fundamental.validation.rule.Rule;
import org.ddd.fundamental.validation.rule.RuleManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.matchers.CapturesArguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class DomainModelTest {

    @Test
    public void testDomainModelAddRule() {
        Order order = new Order("咖啡机订单",60);
        RuleManager ruleManager = Mockito.mock(RuleManager.class);
        order.addRule(ruleManager);
        verify(ruleManager).addRule(any());
    }
    @Test
    public void testDomainModelValidate() {
        Order order = Mockito.mock(Order.class,Mockito.CALLS_REAL_METHODS);
        order.validate();
        verify(order).addRule(any());
    }

    public static class Order extends DomainModel<Long> {
        private String name;
        private int amount;

        public Order(String name, int amount) {
            this.amount = amount;
            this.name = name;
        }

        @Override
        public void addRule(RuleManager ruleManager) {
            super.addRule(ruleManager);
            ruleManager.addRule(new ObjectNotNullRule("name", this.name)
                    .and(new LE("amount", this.amount,50)));
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }
    }
}
