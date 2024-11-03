package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.equipment.value.RPAccountValue;
import org.junit.Assert;
import org.junit.Test;

public class RPAccountTest {

    @Test
    public void testCreateRPAccount(){
        RPAccount account = new RPAccount(
                RPAccountValue.create(1,"SUT","二级使用时间")
        );
        Assert.assertEquals(account.getAccountValue().getAccountNo(),1);
        Assert.assertEquals(account.getAccountValue().getAbbreviation(),"SUT");
        Assert.assertEquals(account.getAccountValue().getDescription(),"二级使用时间");
    }
}
