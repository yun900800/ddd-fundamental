package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.base.ParameterValidationResult;

public class AndRule implements Rule{

    public AndRule(Rule rule,Rule other) {

    }
    @Override
    public ParameterValidationResult validate() {
        return null;
    }

    @Override
    public Rule and(Rule rule) {
        return new AndRule(this, rule);
    }

    @Override
    public Rule or(Rule rule) {
        return null;
    }
}
