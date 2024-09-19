package org.ddd.fundamental.domain.order;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.IKey;
import org.ddd.fundamental.helper.DeepCopyUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定义一个单据的接口
 * 这个单据的接口实际上对应了三个不同的泛型类型的KEY
 * 它们分别是订单对应的key,订单明细对应的key,以及客户信息对应的key
 * 实际上应该是三个不同的泛型参数,但是因为所有的key都是同一个类型实际上可以定义一个
 */
public interface IOrder<K, I extends IItem<K>> extends IKey<K> {
    /**
     * 单据名字
     * @return
     */
    String name();

    IOrder<K,I> nextStatus();

    List<I> items();

    List<I> mergeItems();

    List<I> mergeItemsByKey();

    /**
     * 单据是否合法
     * @return
     */
    boolean validOrder();

    IOrder<K,I> addItem(IItem<K> item);

    IOrder<K,I> removeItem(K key);

    List<K> itemKeys();

    boolean contains(K key);

    void  setCustomer(ICustomer<K,IOrder<K,IItem<K>>> arg);

    ICustomer<K,IOrder<K,IItem<K>>> getCustomer();

    static <K> List<IItem<K>> mergeItems(List<IItem<K>> items){
        List<IItem<K>> mergeItems = new ArrayList<>();
        Map<ItemType,List<IItem<K>>> itemMap = items.stream()
                .collect(Collectors.groupingBy(IItem::type));
        mergeItems.add(mergeListToItem(itemMap,ItemType.RAW_MATERIAL));
        mergeItems.add(mergeListToItem(itemMap,ItemType.WORK_IN_PROGRESS));
        mergeItems.add(mergeListToItem(itemMap,ItemType.FINISHED_PRODUCT));
        return mergeItems;
    }

    static <K> List<IItem<K>> mergeItemsByKey(List<IItem<K>> items, Set<K> keys){
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
        return copyItem(item0,sumQuantity);
    }

    static <K> IItem<K> copyItem(IItem<K> item, double quantity){
        IItem<K> itemNew = copy(item);
        itemNew.setKey(item.key());
        itemNew.changeQuantity(quantity);
        return itemNew;
    }

    static <K> IItem<K> copy(IItem<K> item){
        return DeepCopyUtil.deepCopy(item);
    }


}
