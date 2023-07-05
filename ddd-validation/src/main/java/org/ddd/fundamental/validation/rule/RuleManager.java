package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.base.CompositeParameterValidateResult;
import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;

import java.util.ArrayList;
import java.util.List;

public class RuleManager implements Validatable {

    //规则拥有者
    private DomainModel owner;

    //规则列表
    private List<Rule> rules = new ArrayList<Rule>();


    /**
     * 增加规则
     * @param rule 规则对象
     */
    public void addRule(Rule rule){
        if(rule != null){
            rules.add(rule);
        }
    }

    public RuleManager(DomainModel owner){
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
                if (embeddedObjectRule.getTarget() == null) {
                    result.addValidationResult(new ParameterValidationResult(false,
                            embeddedObjectRule.getDefaultErrorMessage()));
                } else {
                    ParameterValidationResult validationResult = embeddedObjectRule.getTarget().validate();
                    if(!validationResult.isSuccess()){
                        result.addValidationResult(new ParameterValidationResult(false,
                                "EmbeddedObject validate failed"));
                    }
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
}
