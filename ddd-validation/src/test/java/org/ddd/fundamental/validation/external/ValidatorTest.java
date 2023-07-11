package org.ddd.fundamental.validation.external;

import org.ddd.fundamental.validation.exception.ValidationException;
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
    public void testAddContext() {
        ValidationContext context = Mockito.mock(ValidationContext.class);
        Validator validator = new Validator();
        validator.addContext(context);
        Assert.assertNotNull(validator.getContext());
    }

    @Test
    public void testValidate() {
        ValidationContext context = Mockito.mock(ValidationContext.class);
        ValidationSpecificationBase base = Mockito.mock(ValidationSpecificationBase.class, Answers.CALLS_REAL_METHODS);
        ArgumentCaptor<ValidationContext> argumentCaptor = ArgumentCaptor.forClass(ValidationContext.class);
        Validator validator = new Validator();
        validator.addContext(context);
        validator.addSpecification(base);
        validator.validate(context);
        verify(base, Mockito.times(1)).validate(argumentCaptor.capture());
        Assert.assertEquals(argumentCaptor.getValue(),context);
    }

}

