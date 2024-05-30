package org.ddd.fundamental.factory;

import org.ddd.fundamental.helper.ApplicationContextHelper;

public class DomainFactory {
    public static <T> T create(Class<T> entityClz){
        return ApplicationContextHelper.getBean(entityClz);
    }
}
