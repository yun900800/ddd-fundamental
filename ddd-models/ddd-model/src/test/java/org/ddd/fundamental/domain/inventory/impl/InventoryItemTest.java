package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.inventory.IStoreHouse;
import org.ddd.fundamental.domain.order.impl.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class InventoryItemTest {

    @Test
    public void testCreateMaterialInventoryItem() {
        String storeHouseKey = UUID.randomUUID().toString();
        IStoreHouse<String> storeHouse = new StoreHouse("这是原材料仓库",
                ItemType.RAW_MATERIAL);
        storeHouse.setKey(storeHouseKey);

        String materialNumber  = UUID.randomUUID().toString();
        InventoryItem inventoryItem = new MaterialInventoryItem(
                materialNumber, storeHouse.key(), "原材料明细",
                1000
        );

        Assert.assertEquals(inventoryItem.materialNumber(),materialNumber);
        Assert.assertEquals(inventoryItem.storeHouseKey(),storeHouseKey);
        Assert.assertEquals(inventoryItem.name(),"原材料明细");
        Assert.assertEquals(inventoryItem.quantity(),1000,0);
        Assert.assertEquals(inventoryItem.type(),ItemType.RAW_MATERIAL);

    }

    @Test
    public void testCreateWorkInInventoryItem() {
        String storeHouseKey = UUID.randomUUID().toString();
        IStoreHouse<String> storeHouse = new StoreHouse("这是半成品仓库",
                ItemType.WORK_IN_PROGRESS);
        storeHouse.setKey(storeHouseKey);

        String materialNumber  = UUID.randomUUID().toString();
        InventoryItem inventoryItem = new WorkInInventoryItem(
                materialNumber, storeHouse.key(), "半成品明细",
                1200
        );

        Assert.assertEquals(inventoryItem.materialNumber(),materialNumber);
        Assert.assertEquals(inventoryItem.storeHouseKey(),storeHouseKey);
        Assert.assertEquals(inventoryItem.name(),"半成品明细");
        Assert.assertEquals(inventoryItem.quantity(),1200,0);
        Assert.assertEquals(inventoryItem.type(),ItemType.WORK_IN_PROGRESS);

    }

    @Test
    public void testCreateProductInventoryItem() {
        String storeHouseKey = UUID.randomUUID().toString();
        IStoreHouse<String> storeHouse = new StoreHouse("这是成品仓库",
                ItemType.FINISHED_PRODUCT);
        storeHouse.setKey(storeHouseKey);

        String materialNumber  = UUID.randomUUID().toString();
        InventoryItem inventoryItem = new ProductInventoryItem(
                materialNumber, storeHouse.key(), "成品明细",
                1500
        );

        Assert.assertEquals(inventoryItem.materialNumber(),materialNumber);
        Assert.assertEquals(inventoryItem.storeHouseKey(),storeHouseKey);
        Assert.assertEquals(inventoryItem.name(),"成品明细");
        Assert.assertEquals(inventoryItem.quantity(),1500,0);
        Assert.assertEquals(inventoryItem.type(),ItemType.FINISHED_PRODUCT);

    }
}
