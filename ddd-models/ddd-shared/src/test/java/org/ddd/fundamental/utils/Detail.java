package org.ddd.fundamental.utils;

public class Detail {

    private String name;
    private String condition;

    public Detail() {
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
