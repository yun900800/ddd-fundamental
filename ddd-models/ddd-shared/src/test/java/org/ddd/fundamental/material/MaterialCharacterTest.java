package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

public class MaterialCharacterTest {

    @Test
    public void testCreateCharacter(){
        MaterialCharacter materialCharacter= MaterialCharacter.create(
                "code","zh-005","瓶"
        );
        Assert.assertEquals(materialCharacter.toString(),"{\"code\":\"code\",\"spec\":\"zh-005\",\"unit\":\"瓶\"}");
    }
}
