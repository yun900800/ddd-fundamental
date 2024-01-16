package org.ddd.fundamental.core.domain.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class ValueObjectTest {

    @Test
    public void testValueObjectCreate() {
        StringValueObject value = new StringValueObject("nice");
        Assert.assertTrue(value.equals("nice"));
        Assert.assertTrue(value.hashCode()== "nice".hashCode());
    }
}

class StringValueObject extends ValueObject{

    private String value;

    public StringValueObject(String value){
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof String)) {
            return false;
        }
        return obj.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
