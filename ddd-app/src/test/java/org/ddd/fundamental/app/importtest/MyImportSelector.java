package org.ddd.fundamental.app.importtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements DeferredImportSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyImportSelector.class);

    @Override
    public String[] selectImports (AnnotationMetadata importingClassMetadata) {
        LOGGER.info("start selectImports in MyImportSelector");
        String prop = System.getProperty("config");
        if ("MyConfig1".equals(prop)) {
            return new String[]{MyConfig1.class.getName()};
        } else if ("MyConfig2".equals(prop)) {
            return new String[]{MyConfig2.class.getName()};
        } else {
            return new String[]{};
        }
    }
}
