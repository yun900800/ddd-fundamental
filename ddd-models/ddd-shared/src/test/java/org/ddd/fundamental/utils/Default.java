package org.ddd.fundamental.utils;

import java.util.List;

public class Default {

    private String name;
    private String description;

    @Override
    public String toString() {
        return "Default{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", details=" + details +
                '}';
    }

    private List<Detail> details;

    public Default() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Detail> getDetails() {
        return details;
    }
}
