package org.ddd.fundamental.datasource.beans;

public class MyChildBean extends MyBean{


    public MyChildBean(String name) {
        super(name);
    }

    public void initAge() {
        super.initAge();
    }

    private String printInfo() {
        return "name:"+super.getName()+";age:"+ super.getAge();
    }
}
