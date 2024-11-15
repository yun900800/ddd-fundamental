package org.ddd.fundamental.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CollectionUtils {

    public static <T> T random(List<T> collection){
        if (null == collection || collection.size() == 0) {
            return null;
        }
        int size = collection.size();
        int randomElementIndex
                = ThreadLocalRandom.current().nextInt(size) % size;
        return collection.get(randomElementIndex);
    }

}
