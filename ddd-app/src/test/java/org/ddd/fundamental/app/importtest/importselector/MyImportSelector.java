package org.ddd.fundamental.app.importtest.importselector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        String prop = System.getProperty("config");
        if ("FirstConfig".equals(prop)) {
            return new String[]{FirstConfig.class.getName()};
        } else if("SecondConfig".equals(prop)) {
            return new String[]{SecondConfig.class.getName()};
        } else {
            return new String[]{};
        }
    }
}
