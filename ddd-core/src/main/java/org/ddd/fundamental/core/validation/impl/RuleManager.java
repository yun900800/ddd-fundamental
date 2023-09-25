package org.ddd.fundamental.core.validation.impl;

import org.ddd.fundamental.core.validation.Rule;
import org.ddd.fundamental.core.validation.Validation;

import java.util.ArrayList;
import java.util.List;

public class RuleManager implements Validation {

    private List<Rule> rules = new ArrayList<>();

    public void addRule(Rule rule){
        if(rule != null){
            rules.add(rule);
        }
    }

    @Override
    public void validate() {

    }
}
