package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.inventory.IStoreHouse;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class StoreHouseTest {

    @Test
    public void testCreateStoreHouse() {
        IStoreHouse<String> storeHouse = StoreHouse.createStoreHouse("这是原材料仓库",
                ItemType.RAW_MATERIAL);
        String key = UUID.randomUUID().toString();
        storeHouse.setKey(key);
        Assert.assertEquals(storeHouse.toString(),"StoreHouse{key='"+key+"', name='这是原材料仓库', storeType='原材料'}");
    }

    @Test
    public void testStoreHouseName() {
        IStoreHouse<String> storeHouse = StoreHouse.createStoreHouse("这是原材料仓库",
                ItemType.RAW_MATERIAL);
        Assert.assertEquals(storeHouse.name(),"这是原材料仓库");
        Assert.assertEquals(storeHouse.storeType(),ItemType.RAW_MATERIAL);
    }
}
