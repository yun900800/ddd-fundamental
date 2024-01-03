package org.ddd.fundamental.validation.exception.external;

import org.ddd.fundamental.validation.exception.ValidationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Validator implements Validationable {
    //验证规则列表
    private List<ValidationSpecificationBase> specifications = new ArrayList<>();

    private ValidationContext validationContext;

    public Validator() {
    }

    public Validator(ValidationContext validationContext) {
        this.validationContext = validationContext;
    }

    /**
     * 验证方法
     * @param validationContext  验证上下文
     * @throws ValidationException 验证异常
     */
    @Override
    public void validate(final ValidationContext validationContext) throws ValidationException {
        Iterator<ValidationSpecificationBase> iterator  = this.specifications.iterator();
        while (iterator.hasNext()) {
            ValidationSpecificationBase validationSpecification = iterator.next();
            validationSpecification.validate(validationContext);
        }
        clearSpecifications();
    }

    private void clearSpecifications() {
        this.specifications.clear();
        this.validationContext = null;
    }

    public Validator addSpecification(ValidationSpecificationBase validationSpecification){
        this.specifications.add(validationSpecification);
        return this;
    }

    public ValidationContext getContext() {
        return this.validationContext;
    }

    public List<ValidationSpecificationBase> getSpecifications() {
        return specifications;
    }
}
