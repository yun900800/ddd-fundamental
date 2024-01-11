package org.ddd.fundamental.spring.core.resolvedependency.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyServiceB {
    /**
     * 方法注入
     */
    private MyServiceA methodMyServiceA;

    /**
     * 字段注入
     */
    private MyServiceA fieldMyServiceA;

    /**
     * 字段注入 (环境变量)
     */
    @Value("${my.property.value}")
    private String myPropertyValue;

    public void setMethodMyServiceA(MyServiceA methodMyServiceA){
        this.methodMyServiceA = methodMyServiceA;
    }

    @Override
    public String toString() {
        return "MyServiceB{" +
                "myPropertyValue='" + myPropertyValue + '\'' +
                ", methodMyServiceA=" + methodMyServiceA +
                ", fieldMyServiceA=" + fieldMyServiceA +
                '}';
    }
}
