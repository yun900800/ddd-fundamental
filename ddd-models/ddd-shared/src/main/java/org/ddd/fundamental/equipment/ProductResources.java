package org.ddd.fundamental.equipment;

public interface ProductResources {

    void input(ProductInput input);

    ProductOutput output();

    String resourceName();

}
