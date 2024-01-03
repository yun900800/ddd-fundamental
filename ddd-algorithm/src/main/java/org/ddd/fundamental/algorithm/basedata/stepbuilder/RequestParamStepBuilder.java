package org.ddd.fundamental.algorithm.basedata.stepbuilder;

public final class RequestParamStepBuilder {

    private RequestParamStepBuilder(){
    }

    /**
     * 公布对外唯一的接口,同时将对重要的属性接口作为返回值,避免返回
     * RequestParamStep对象，因为这个对象方法太多，客户端调用不清楚该怎么使用
     * @return
     */
    public static UrlStep newBuilder() {
        return new RequestParamStep();
    }

    public interface UrlStep {
        MethodStep url(String url);
    }

    public interface MethodStep{
        PostStep postMethod(String method);

        GetStep getMethod(String method);
    }

    public interface PostStep {
        BuilderStep body(String body);
    }


    public interface GetStep{
        BuilderStep queryParams(String params);
    }

    public interface BuilderStep {
        RequestParam  build();
    }

    private static class RequestParamStep implements UrlStep,
            MethodStep, PostStep, GetStep, BuilderStep {

        private String url;

        //分支
        private String method;

        //method post
        private String body;

        //method get
        private String queryParams;

        // 1. 注意这里如果存在必填参数的话,最好设计到构造函数里面,选填的参数放置到接口方法中.
        // 2. builder方法需要注意,区分属性是必填的还是选填的;区分在构造过程中是否存在分叉


        @Override
        public MethodStep url(String url) {
            this.url = url;
            return this;
        }

        @Override
        public PostStep postMethod(String method) {
            this.method = method;
            return this;
        }

        @Override
        public GetStep getMethod(String method) {
            this.method = method;
            return this;
        }

        @Override
        public BuilderStep body(String body) {
            this.body = body;
            return this;
        }

        @Override
        public BuilderStep queryParams(String params) {
            this.queryParams = params;
            return this;
        }

        @Override
        public RequestParam build() {
            RequestParam param = new RequestParam(url);
            param.setMethod(method);
            if (this.body!= null) {
                param.setBody(body);
            }
            if (this.queryParams != null) {
                param.setQueryParams(queryParams);
            }
            return param;
        }
    }


}
