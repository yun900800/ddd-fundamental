package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.base.CompositeParameterValidateResult;
import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类可以认为是一个聚合
 * 它聚合了各种验证规则和验证对象,然后在validate方法中进行验证
 * 这里的规则其实使用了specification模式
 * @param <T>
 */
public class RuleManager<T> implements Validatable {

    //规则拥有者
    private DomainModel<T> owner;

    //规则列表
    private List<Rule> rules = new ArrayList<>();


    /**
     * 增加规则
     * @param rule 规则对象
     */
    public void addRule(Rule rule){
        if(rule != null){
            rules.add(rule);
        }
    }

    public RuleManager(DomainModel<T> owner){
        this.owner = owner;
    }

    /**
     * 执行验证，调用规则的验证方法来执行具体的验证。
     * @return 验证结果
     */
    public ParameterValidationResult validate(){
        CompositeParameterValidateResult result = new CompositeParameterValidateResult();
        for(Rule rule : this.rules){
            //针对嵌入式对象的验证
            if (rule instanceof EmbeddedObjectRule){
                EmbeddedObjectRule embeddedObjectRule = (EmbeddedObjectRule) rule;
                ParameterValidationResult validationResult = embeddedObjectRule.getTarget().validate();
                if(!validationResult.isSuccess()){
                    result.addValidationResult(new ParameterValidationResult(false,
                            "EmbeddedObject validate failed:"+ validationResult.getMessage()));
                }
                continue;
            }
            ParameterValidationResult ruleVerifyResult = rule.validate();
            if(!ruleVerifyResult.isSuccess()){
                result.fail();
                result.addValidationResult(new ParameterValidationResult(false, ruleVerifyResult.getMessage()));
            }
        }
        return result;
    }

    public DomainModel<T> getOwner() {
        return owner;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
