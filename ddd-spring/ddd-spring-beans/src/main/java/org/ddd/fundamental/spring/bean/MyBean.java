package org.ddd.fundamental.spring.bean;

public class MyBean {
    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void init(){
        System.out.println("execute com.xcs.spring.bean.MyBean.init");
    }

    public void destroy(){
        System.out.println("execute com.xcs.spring.bean.MyBean.destroy");
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
