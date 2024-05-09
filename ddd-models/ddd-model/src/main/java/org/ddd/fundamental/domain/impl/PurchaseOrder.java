package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.IItem;
import org.ddd.fundamental.domain.IOrder;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PurchaseOrder implements IOrder<String> {

    private String name;

    private List<IItem<String>> items = new ArrayList<>();

    public PurchaseOrder(String name){
        this.name = name;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public IOrder<String> nextStatus() {
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
        return createItemByKey(key,item0,sumQuantity);
    }

    private static IItem<String> createItemByKey(String key,IItem<String> item, double quantity){
        IItem<String> itemNew = null;
        if (ItemType.RAW_MATERIAL == item.type()) {
            itemNew = new RawMaterial(item.name(),quantity);
        } else if (ItemType.WORK_IN_PROGRESS == item.type()) {
            itemNew = new WorkInProgress(item.name(),quantity);
        } else if (ItemType.FINISHED_PRODUCT == item.type()) {
            itemNew = new FinishedProduct(item.name(),quantity);
        }
        return itemNew;
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
    public IOrder<String> addItem(IItem<String> item) {
        items.add(item);
        return this;
    }

    @Override
    public IOrder<String> removeItem(String key){
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
