package org.ddd.fundamental.app.importtest.importselector;

public class AppBean {
    private String message;
    public AppBean(){
        this("default message");
    }

    public AppBean(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
