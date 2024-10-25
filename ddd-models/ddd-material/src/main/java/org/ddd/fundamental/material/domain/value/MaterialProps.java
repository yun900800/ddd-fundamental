package org.ddd.fundamental.material.domain.value;


import com.alibaba.fastjson.JSON;
import org.ddd.fundamental.core.ValueObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义物料属性值对象
 */
public class MaterialProps implements ValueObject {
    private String purpose;

    private String storageCondition;

    private String purchaseCycle;

    @SuppressWarnings("unused")
    MaterialProps(){}

    public MaterialProps(String purpose,
                         String storageCondition,
                         String purchaseCycle){
        this.purpose = purpose;
        this.storageCondition = storageCondition;
        this.purchaseCycle = purchaseCycle;
    }

    public Map<String,String> toMap() {
        String jsonString = JSON.toJSONString(this);
        return JSON.parseObject(jsonString,Map.class);
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public String getPurchaseCycle() {
        return purchaseCycle;
    }
}
