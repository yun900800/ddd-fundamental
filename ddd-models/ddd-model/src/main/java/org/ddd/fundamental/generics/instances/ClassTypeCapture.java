package org.ddd.fundamental.generics.instances;

import java.util.HashMap;
import java.util.Map;

public class ClassTypeCapture {
    private Map<String, FactoryI<?, Void>> classMap = new HashMap<>();

    public static void main(String[] args) {
        ClassTypeCapture ctc = new ClassTypeCapture();
        ctc.addType("Integer", new IntegerFactory());
        ctc.addType("Widget", new Widget.Factory());

        System.out.println(ctc.createNew("Integer"));
        System.out.println(ctc.createNew("Widget"));
        System.out.println(ctc.createNew("String"));
    }

    private void addType(String typename, FactoryI<?, Void> kind) {
        classMap.put(typename, kind);
    }

    private Object createNew(String typename) {
        return classMap.get(typename).create(null);
    }
}
