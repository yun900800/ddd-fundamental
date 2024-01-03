package org.ddd.fundamental.validation.exception.external;

import org.ddd.fundamental.validation.exception.ValidationException;

public abstract class ValidationServiceBase implements Validationable{
    private ThreadLocal<Validator> validatorThreadLocal = new ThreadLocal<>();

    /**
     * 验证服务
     *
     * @param validationContext 验证信息上下文
     * @throws ValidationException 验证异常
     */
    @Override
    public void validate(final ValidationContext validationContext) throws ValidationException {
        this.validatorThreadLocal.set(new Validator(validationContext));
        this.buildValidator(this.validatorThreadLocal.get());
        this.validatorThreadLocal.get().validate(validationContext);
    }

    /**
     * 构建验证器
     * @param validator 验证器
     */
    protected abstract void buildValidator(Validator validator);
}
