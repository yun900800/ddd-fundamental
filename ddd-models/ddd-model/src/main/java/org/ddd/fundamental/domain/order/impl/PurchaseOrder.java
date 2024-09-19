package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.order.ICustomer;
import org.ddd.fundamental.domain.order.IItem;
import org.ddd.fundamental.domain.order.IOrder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PurchaseOrder implements IOrder<String,IItem<String>> {

    private String name;

    private String key;

    private List<IItem<String>> items = new ArrayList<>();

    public PurchaseOrder(String name){
        this.name = name;
        this.key = UUID.randomUUID().toString();
    }

    private ICustomer<String, IOrder<String,IItem<String>>> customer;

    @Override
    public ICustomer<String, IOrder<String,IItem<String>>> getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(ICustomer<String, IOrder<String,IItem<String>>> arg) {
        if (null != customer) {
            customer.friendOrders().remove(this);
        }
        this.customer = arg;
        if (null != customer) {
            customer.friendOrders().add(this);
        }
    }


    @Override
    public String key() {
        if (!StringUtils.hasLength(this.key)) {
            this.key = UUID.randomUUID().toString();
        }
        return key;
    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public IOrder<String,IItem<String>> nextStatus() {
        return null;
    }

    @Override
    public List<IItem<String>> items() {
        return new ArrayList<>(items);
    }

    /**
     * 返回合并以后的订单明细
     * @return
     */
    @Override
    public List<IItem<String>> mergeItems() {
        List<IItem<String>> mergeItems = new ArrayList<>();
        Map<ItemType,List<IItem<String>>> itemMap = items.stream()
                .collect(Collectors.groupingBy(IItem::type));
        mergeItems.add(mergeListToItem(itemMap,ItemType.RAW_MATERIAL));
        mergeItems.add(mergeListToItem(itemMap,ItemType.WORK_IN_PROGRESS));
        mergeItems.add(mergeListToItem(itemMap,ItemType.FINISHED_PRODUCT));
        return mergeItems;
    }

    @Override
    public List<IItem<String>> mergeItemsByKey() {
        List<IItem<String>> mergeItems = new ArrayList<>();
        Map<String,List<IItem<String>>> itemMap = items.stream()
                .collect(Collectors.groupingBy(IItem::key));
        for (String key: keys()){
            mergeItems.add(mergeListToItemByKey(itemMap,key));
        }
        return mergeItems;
    }

    private IItem<String> mergeListToItemByKey(Map<String,List<IItem<String>>> itemMap,String key) {
        List<IItem<String>> materialItems = itemMap.get(key);
        if (CollectionUtils.isEmpty(materialItems)) {
            return null;
        }
        IItem<String> item0 = materialItems.get(0);
        double sumQuantity = 0.0;
        for (IItem<String> item: materialItems) {
            sumQuantity+=item.quantity();
        }
        return Item.copy(item0).changeQuantity(sumQuantity);
    }

    private static IItem<String> createItem(ItemType type,IItem<String> item, double quantity){
        IItem<String> itemNew = null;
        if (ItemType.RAW_MATERIAL == type) {
            itemNew = new RawMaterial(item.name(),quantity);
        } else if (ItemType.WORK_IN_PROGRESS == type) {
            itemNew = new WorkInProgress(item.name(),quantity);
        } else if (ItemType.FINISHED_PRODUCT == type) {
            itemNew = new FinishedProduct(item.name(),quantity);
        }
        return itemNew;
    }

    private IItem<String> mergeListToItem(Map<ItemType,List<IItem<String>>> itemMap,ItemType type) {
        List<IItem<String>> materialItems = itemMap.get(type);
        if (CollectionUtils.isEmpty(materialItems)) {
            return null;
        }
        IItem<String> item0 = materialItems.get(0);
        double sumQuantity = 0.0;
        for (IItem<String> item: materialItems) {
            sumQuantity+=item.quantity();
        }
        return createItem(type,item0,sumQuantity);
    }

    @Override
    public boolean validOrder() {
        return !CollectionUtils.isEmpty(items());
    }

    @Override
    public IOrder<String,IItem<String>> addItem(IItem<String> item) {
        items.add(item);
        return this;
    }

    @Override
    public IOrder<String,IItem<String>> removeItem(String key){
        items = items.stream().filter(
                item->key != item.key())
                .collect(Collectors.toList());
        return this;
    }


    @Override
    public List<String> itemKeys() {
        List<String> keys = new ArrayList<>();
        for (IItem<String> item: items()){
            keys.add(item.key());
        }
        return keys;
    }

    @Override
    public boolean contains(String key) {
        Set<String> keys = new HashSet<>(itemKeys());
        return keys.contains(key);
    }

    public Set<String> keys() {
        Set<String> keys = new HashSet<>(itemKeys());
        return keys;
    }
}
