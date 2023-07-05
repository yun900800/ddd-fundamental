package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.EmbeddedObjectRule;
import org.ddd.fundamental.validation.rule.LE;
import org.ddd.fundamental.validation.rule.ObjectNotNullRule;
import org.ddd.fundamental.validation.rule.RuleManager;

public class Order extends DomainModel<Long>{
    private String name;
    private Contact contact;

    private int amount;

    public Order(String name, Contact contact) {
        this.name = name;
        this.contact = contact;
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
