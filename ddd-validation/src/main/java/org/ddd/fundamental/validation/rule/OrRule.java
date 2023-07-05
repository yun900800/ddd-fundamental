package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.base.ParameterValidationResult;

public class OrRule implements Rule{

    public OrRule(Rule rule,Rule other) {

    }
    @Override
    public ParameterValidationResult validate() {
        return null;
    }

    @Override
    public Rule and(Rule rule) {
        return null;
    }

    @Override
    public Rule or(Rule rule) {
        return null;
    }
}
