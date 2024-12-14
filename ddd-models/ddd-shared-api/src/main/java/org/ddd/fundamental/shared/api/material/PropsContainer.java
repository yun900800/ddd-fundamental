package org.ddd.fundamental.shared.api.material;

public class PropsContainer {

    private String key;

    private String value;

    protected PropsContainer(){}

    private PropsContainer(String key,String value){
        this.key = key;
        this.value = value;
    }

    public static PropsContainer create(String key,String value) {
        return new PropsContainer(key,value);
    }

    @Override
    public String toString() {
        return "PropsContainer{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
