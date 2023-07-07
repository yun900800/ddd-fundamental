package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.Rule;
import org.ddd.fundamental.validation.rule.impl.AndRule;
import org.ddd.fundamental.validation.rule.impl.LE;
import org.ddd.fundamental.validation.rule.impl.ObjectNotNullRule;
import org.ddd.fundamental.validation.rule.impl.RuleManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class DomainModelTest {

    @Test
    public void testDomainModelAddRule() {
        ArgumentCaptor<Rule> argumentCaptor = ArgumentCaptor.forClass(Rule.class);
        Order order = new Order("咖啡机订单",60);
        RuleManager ruleManager = Mockito.mock(RuleManager.class);
        order.addRule(ruleManager);
        //verify(ruleManager).addRule(any());
        //上面是没有捕捉参数的验证，下面是有捕捉参数的验证
        verify(ruleManager).addRule(argumentCaptor.capture());
        Assert.assertTrue(argumentCaptor.getValue() instanceof AndRule);
        Assert.assertTrue(((AndRule)argumentCaptor.getValue()).getRule() instanceof ObjectNotNullRule);
        Assert.assertTrue(((AndRule)argumentCaptor.getValue()).getOther() instanceof LE);
    }
    @Test
    public void testDomainModelOrValidatableBseeValidate() {
        ArgumentCaptor<RuleManager> argumentCaptor = ArgumentCaptor.forClass(RuleManager.class);
        Order order = Mockito.mock(Order.class,Mockito.CALLS_REAL_METHODS);
        //这里有个问题是似乎 validate方法中的RuleManager类不好测试
        order.validate();
        //verify(order).addRule(any());
        //上面是没有捕捉参数的验证，下面是有捕捉参数的验证
        verify(order).addRule(argumentCaptor.capture());
        Assert.assertTrue(argumentCaptor.getValue() instanceof RuleManager);
        Assert.assertEquals(argumentCaptor.getValue().getOwner(),order);
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
