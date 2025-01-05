package org.ddd.fundamental.utils;

import java.util.Arrays;
import java.util.List;

public class Config {

    private Integer id;
    private String name;
    private Boolean enabled;
    private Integer[] clearances;
    private Default defaultDescription;
    private List<Default> descriptions;

    public Config() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer[] getClearances() {
        return clearances;
    }

    public Default getDefaultDescription() {
        return defaultDescription;
    }

    public List<Default> getDescriptions() {
        return descriptions;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", clearances=" + Arrays.toString(clearances) +
                ", defaultDescription=" + defaultDescription +
                ", descriptions=" + descriptions +
                '}';
    }
}
