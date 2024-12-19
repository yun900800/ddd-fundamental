package org.ddd.fundamental.common.group;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GroupUtils {

    public static <K,T> Map<K, Collection<T>> group(List<T> dataList){
        Multimap<K,T> multimap = ArrayListMultimap.create();
        for (T data: dataList) {
            if (data instanceof GroupKey<?>){
                K key = ((GroupKey<K>) data).key();
                multimap.put(key,data);
            }
        }
        return multimap.asMap();
    }
}
