package org.ddd.fundamental.validation.exception.external;

import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.base.ParameterValidationResult;

public abstract class VOBase implements Validatable {
    @Override
    public ParameterValidationResult validate() {
        return ParameterValidationResult.success();
    }
}
