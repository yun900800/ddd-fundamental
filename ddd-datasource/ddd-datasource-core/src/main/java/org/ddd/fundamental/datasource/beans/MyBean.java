package org.ddd.fundamental.datasource.beans;

public class MyBean {

    private String name;

    private Integer age;

    public MyBean(String name) {
        this.name = name;
    }

    public void initAge() {
        this.age = 20;
    }

    public void changeAge(Integer age) {
        this.age = age;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public static String printClassName() {
        return MyBean.class.getName();
    }
}
