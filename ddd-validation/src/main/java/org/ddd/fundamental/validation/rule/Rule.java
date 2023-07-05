package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.Validatable;

public interface Rule extends Validatable {
    /**
     * 与操作
     * @param rule 目标规则
     * @return 与后的规则
     */
    Rule and(Rule rule);

    /**
     * 或操作
     * @param rule 目标规则
     * @return 或后的规则
     */
    Rule or(Rule rule);
}