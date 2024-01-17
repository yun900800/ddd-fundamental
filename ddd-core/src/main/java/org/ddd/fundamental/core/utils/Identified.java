package org.ddd.fundamental.core.utils;

import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public interface Identified {

    String getIdentifier();

    static boolean isDuplicated(Collection<? extends Identified> collection) {
        if (isEmpty(collection)) {
            return false;
        }

        long count = collection.stream().map(Identified::getIdentifier).distinct().count();
        return count != collection.size();
    }
}
