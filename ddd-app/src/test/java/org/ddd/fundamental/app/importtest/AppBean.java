package org.ddd.fundamental.app.importtest;

public class AppBean {
    private String message;

    public AppBean(){}

    public AppBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
