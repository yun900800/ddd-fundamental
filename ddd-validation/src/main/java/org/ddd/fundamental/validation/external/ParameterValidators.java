package org.ddd.fundamental.validation.external;

import org.apache.commons.lang3.StringUtils;
import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.base.ParameterValidationResult;

import java.util.ArrayList;
import java.util.List;

public class ParameterValidators {

    private List<Validatable> parameters = new ArrayList<>();
    /**
     * 验证
     * @throws IllegalArgumentException 参数异常
     */
    public void validate() throws IllegalArgumentException {
        if (this.parameters.isEmpty()) {
            return;
        }
        for (Validatable parameter : this.parameters) {
            ParameterValidationResult validationResult = parameter.validate();
            if (!validationResult.isSuccess()) {
                throw new IllegalArgumentException(validationResult.getMessage());
            }
        }
    }


    /**
     * 增加待验证的视图模型
     * @param vo 视图模型
     * @param messageIfVoIsNull 当视图模型为空时的提示信息
     */
    public ParameterValidators addVoRule(VOBase vo, String messageIfVoIsNull) {
        this.parameters.add(new VORule(vo, messageIfVoIsNull));
        return this;
    }


    /**
     * 增加业务模型ID验证
     * @param targetValue 待验证参数的值
     * @param errorMessage 错误提示
     * @return 参数验证器
     */
    public ParameterValidators addStringNotNullRule(String targetValue, String errorMessage) {
        this.parameters.add(new StringNotNullRule(targetValue, errorMessage));
        return this;
    }

    public ParameterValidators addObjectNotNullRule(Object targetValue,String errorMessage) {
        this.parameters.add(new ObjectNotNullRule(targetValue, errorMessage));
        return this;
    }

    public static ParameterValidators build() {
        return new ParameterValidators();
    }
}

class VORule implements Validatable {
    private VOBase vo;
    private String messageIfVoIsNull;

    public VORule(VOBase vo,String messageIfVoIsNull) {
        this.vo = vo;
        this.messageIfVoIsNull = messageIfVoIsNull;
    }


    @Override
    public ParameterValidationResult validate() {
        if (vo == null) {
            if (StringUtils.isEmpty(messageIfVoIsNull)) {
                return ParameterValidationResult.failed("failed");
            }
            return ParameterValidationResult.failed(messageIfVoIsNull);
        }
        ParameterValidationResult validationResult = vo.validate();
        if (!validationResult.isSuccess()) {
            return ParameterValidationResult.failed(validationResult.getMessage());
        }
        return ParameterValidationResult.success();
    }
}

class StringNotNullRule implements Validatable {

    private String targetValue;

    private String errorMessage;
    public StringNotNullRule(String targetValue,String errorMessage) {
        this.targetValue = targetValue;
        this.errorMessage = errorMessage;
    }
    @Override
    public ParameterValidationResult validate() {
        if (StringUtils.isEmpty(targetValue)) {
            return ParameterValidationResult.failed(errorMessage);
        }
        return ParameterValidationResult.success();
    }
}

class ObjectNotNullRule implements Validatable {
    private Object targetValue;

    private String errorMessage;

    public ObjectNotNullRule(Object targetValue,String errorMessage) {
        this.targetValue = targetValue;
        this.errorMessage = errorMessage;
    }
    @Override
    public ParameterValidationResult validate() {
        if (null == targetValue) {
            return ParameterValidationResult.failed(errorMessage);
        }
        return ParameterValidationResult.success();
    }
}

