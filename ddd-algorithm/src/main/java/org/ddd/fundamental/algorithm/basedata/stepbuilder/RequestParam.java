package org.ddd.fundamental.algorithm.basedata.stepbuilder;

public class RequestParam {

    //必填
    private String url;

    //分支
    private String method;

    //method post
    private String body;

    //method get
    private String queryParams;

    void setUrl(String url) {
        this.url = url;
    }

    void setMethod(String method) {
        this.method = method;
    }

    void setBody(String body) {
        this.body = body;
    }

    void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    RequestParam(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getQueryParams() {
        return queryParams;
    }
}
