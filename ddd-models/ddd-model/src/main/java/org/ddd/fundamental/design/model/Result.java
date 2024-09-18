package org.ddd.fundamental.design.model;

public class Result {

    private String code;

    private String message;

    private Result(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return code.equals("0000");
    }

    public static Result success(){
        return new Result("0000","success");
    }

    public static Result failure(String code){
        return new Result(code,"failure");
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
