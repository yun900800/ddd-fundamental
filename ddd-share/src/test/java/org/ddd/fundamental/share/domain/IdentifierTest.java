package org.ddd.fundamental.share.domain;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

public class IdentifierTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCreateInvalidIdentifier() {
        //定义期望，然后执行
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid UUID string: 123");
        Identifier identifier = new EmptyIdentifier("123");
    }

    @Test
    public void testCreateValidIdentifier() {
        String uuid = UUID.randomUUID().toString();
        Identifier identifier = new EmptyIdentifier(uuid);
        Assert.assertEquals(uuid,identifier.value);
    }
}

class EmptyIdentifier extends Identifier{
    private String empty;

    public EmptyIdentifier(String empty){
        super(empty);
    }
}


