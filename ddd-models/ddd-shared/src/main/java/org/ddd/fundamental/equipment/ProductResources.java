package org.ddd.fundamental.equipment;

import org.ddd.fundamental.equipment.ProductInput;
import org.ddd.fundamental.equipment.ProductOutput;

public interface ProductResources {

    void input(ProductInput input);

    ProductOutput output();

    String resourceName();

}
