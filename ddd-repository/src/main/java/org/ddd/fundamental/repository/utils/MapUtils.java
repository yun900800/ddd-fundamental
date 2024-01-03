package org.ddd.fundamental.repository.utils;

import java.util.HashMap;
import java.util.Map;

public final class MapUtils {

    public static <KeyType,ValueType> Map<ValueType,KeyType> reverseMap(Map<KeyType,ValueType> originMap){
        Map<ValueType,KeyType> reverseMap = new HashMap<>();
        for (Map.Entry<KeyType,ValueType> entry: originMap.entrySet()) {
            KeyType keyType = entry.getKey();
            ValueType valueType = entry.getValue();
            reverseMap.put(valueType,keyType);
        }
        return reverseMap;
    }
}
