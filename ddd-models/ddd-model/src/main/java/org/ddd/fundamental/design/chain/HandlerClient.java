package org.ddd.fundamental.design.chain;

import org.ddd.fundamental.design.model.ProductVO;
import org.ddd.fundamental.design.model.Result;

public class HandlerClient {

    public static Result executeChain(AbstractCheckHandler handler, ProductVO param) {
        //执行处理器
        Result handlerResult = handler.handle(param);
        if (!handlerResult.isSuccess()) {
            System.out.println("HandlerClient 责任链执行失败返回：" + handlerResult.toString());
            return handlerResult;
        }
        return Result.success();
    }
}
