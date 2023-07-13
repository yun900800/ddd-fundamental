package org.ddd.fundamental.share.domain;

import org.junit.Assert;
import org.junit.Test;

public class StringValueObjectTest {

    @Test
    public void testCreateStringValueObject() {
        StringValueObject empty = new EmptyStringValue("");
        Assert.assertEquals(empty.value(),"");
        StringValueObject empty1 = new EmptyStringValue("");
        Assert.assertEquals(empty,empty1);
    }
}

class EmptyStringValue extends StringValueObject {

    public EmptyStringValue(String empty){
        super(empty);
    }
}
