package org.ddd.fundamental.validation;

import org.ddd.fundamental.validation.base.ParameterValidationResult;

public interface Validatable {
    /**
     * 验证
     * @return 验证结果
     */
    ParameterValidationResult validate();
}
