package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.order.IItem;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Item implements IItem<String> {

    private String name;

    private double quantity;

    private String key;

    public Item(String name,double quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public void setKey(String key){
        this.key = key;
    }

    public static IItem<String> copy(IItem<String> item){
        IItem<String> copyItem = new Item(item.name(),item.quantity()) {
            @Override
            protected String typeName() {
                return item.type().getName();
            }

            @Override
            public ItemType type() {
                return item.type();
            }
        };
        copyItem.setKey(item.key());
        return copyItem;
    }

    public static <K> List<IItem<K>> mergeItems(List<IItem<K>> items){
        List<IItem<K>> mergeItems = new ArrayList<>();
        Map<ItemType,List<IItem<K>>> itemMap = items.stream()
                .collect(Collectors.groupingBy(IItem::type));
        mergeItems.add(mergeListToItem(itemMap,ItemType.RAW_MATERIAL));
        mergeItems.add(mergeListToItem(itemMap,ItemType.WORK_IN_PROGRESS));
        mergeItems.add(mergeListToItem(itemMap,ItemType.FINISHED_PRODUCT));
        return mergeItems;
    }

    public static <K> List<IItem<K>> mergeItemsByKey(List<IItem<K>> items, Set<K> keys){
        List<IItem<K>> mergeItems = new ArrayList<>();
        Map<K,List<IItem<K>>> itemMap = items.stream()
                .collect(Collectors.groupingBy(IItem::key));
        for (K key: keys){
            mergeItems.add(mergeListToItemByKey(itemMap,key));
        }
        return mergeItems;
    }

    private static <K> IItem<K> mergeListToItemByKey(Map<K,List<IItem<K>>> itemMap,K key) {
        List<IItem<K>> materialItems = itemMap.get(key);
        if (CollectionUtils.isEmpty(materialItems)) {
            return null;
        }
        IItem<K> item0 = materialItems.get(0);
        double sumQuantity = 0.0;
        for (IItem<K> item: materialItems) {
            sumQuantity+=item.quantity();
        }
        item0.changeQuantity(sumQuantity);
        return item0;
    }



    private static <K> IItem<K> mergeListToItem(Map<ItemType,List<IItem<K>>> itemMap,ItemType type) {
        List<IItem<K>> materialItems = itemMap.get(type);
        if (CollectionUtils.isEmpty(materialItems)) {
            return null;
        }
        IItem<K> item0 = materialItems.get(0);
        double sumQuantity = 0.0;
        for (IItem<K> item: materialItems) {
            sumQuantity+=item.quantity();
        }
        return createItem(type,item0,sumQuantity);
    }

    private static <K> IItem<K> createItem(ItemType type,IItem<K> item, double quantity){
        IItem<K> itemNew = item;
        itemNew.changeQuantity(quantity);
        return itemNew;
    }

    @Override
    public String key() {
        if (!StringUtils.hasLength(this.key)) {
            this.key = UUID.randomUUID().toString();
        }
        return key;
    }

    @Override
    public String name() {
        return name;
    }

    protected abstract String typeName();

    @Override
    public double quantity() {
        return this.quantity;
    }

    public Item changeQuantity(double quantity){
        this.quantity = quantity;
        return this;
    }

}
