package org.ddd.fundamental.app.importtest;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports (AnnotationMetadata importingClassMetadata) {
        System.out.println("selectImports");
        String prop = System.getProperty("myProp");
        if ("someValue".equals(prop)) {
            return new String[]{MyConfig1.class.getName()};
        } else {
            return new String[]{MyConfig2.class.getName()};
        }
    }
}
