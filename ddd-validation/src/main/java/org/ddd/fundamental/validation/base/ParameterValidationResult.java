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
        this.fail("failed");
    }

    public void fail(String message) {
        this.success = false;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public static ParameterValidationResult failed(String message) {
        return new ParameterValidationResult(false,message);
    }

    public static ParameterValidationResult failed() {
        return ParameterValidationResult.failed("failed");
    }

    public static ParameterValidationResult success() {
        return ParameterValidationResult.success("success");
    }

    public static ParameterValidationResult success(String message) {
        return new ParameterValidationResult(true,message);
    }
}
