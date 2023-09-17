package org.ddd.fundamental.authentication.domain.vo;

public class AuthResp {
    private int status;

    private String message;


    public AuthResp(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthResp() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
