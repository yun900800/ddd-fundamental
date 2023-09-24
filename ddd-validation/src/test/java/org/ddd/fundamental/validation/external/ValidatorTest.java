package org.ddd.fundamental.validation.external;

import org.ddd.fundamental.validation.exception.external.ValidationContext;
import org.ddd.fundamental.validation.exception.external.ValidationSpecificationBase;
import org.ddd.fundamental.validation.exception.external.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class ValidatorTest {

    @Test
    public void testCreateValidator() {
        Validator validator = new Validator();
        Assert.assertEquals(validator.getSpecifications().size(),0);
        Assert.assertNull(validator.getContext());
    }

    @Test
    public void testAddSpecification() {
        Validator validator = new Validator();
        ValidationSpecificationBase base = Mockito.mock(ValidationSpecificationBase.class, Answers.CALLS_REAL_METHODS);
        validator.addSpecification(base);
        Assert.assertEquals(validator.getSpecifications().size(),1);
        Assert.assertNull(validator.getContext());
    }

    @Test
    public void testValidate() {
        ValidationContext context = Mockito.mock(ValidationContext.class);
        ValidationSpecificationBase base = Mockito.mock(ValidationSpecificationBase.class, Answers.CALLS_REAL_METHODS);
        ArgumentCaptor<ValidationContext> argumentCaptor = ArgumentCaptor.forClass(ValidationContext.class);
        Validator validator = new Validator(context);
        validator.addSpecification(base);
        validator.validate(context);
        verify(base, Mockito.times(1)).validate(argumentCaptor.capture());
        Assert.assertEquals(argumentCaptor.getValue(),context);
    }

}

