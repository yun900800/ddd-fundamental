package org.ddd.fundamental.share.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class IntValueObjectTest {

    @Test
    public void testCreateIntValueObject() {
        IntValueObject iValue = new SimpleIntValue(5);
        Assert.assertEquals(Optional.of(5).get(),iValue.value());

        IntValueObject iValue2 = new SimpleIntValue(5);
        Assert.assertEquals(iValue2,iValue2);
    }
}

class SimpleIntValue extends IntValueObject {
    public SimpleIntValue(Integer value) {
        super(value);
    }
}
