package org.ddd.fundamental.validation.base;

public class ParameterValidationResult {

    private boolean success = false;

    private String message;

    public ParameterValidationResult(boolean success,String message) {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess(){
        return this.success;
    }

    public void fail(){
        this.success = false;
        this.message = "failed";
    }

    public static ParameterValidationResult failed(String message) {
        return new ParameterValidationResult(false,message);
    }

    public String getMessage() {
        return message;
    }

    public static ParameterValidationResult success() {
        return new ParameterValidationResult(true,"success");
    }
}
