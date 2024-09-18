package org.ddd.fundamental.design.chain.impl;

import org.ddd.fundamental.constants.ErrorCode;
import org.ddd.fundamental.design.model.ProductVO;
import org.ddd.fundamental.design.model.Result;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 价格校验处理器
 */
@Component
public class PriceCheckHandler extends AbstractCheckHandler {
    @Override
    public Result handle(ProductVO param) {
        System.out.println("价格校验 Handler 开始...");

        Result result = param.illegalPrice();
        if (!result.isSuccess()) {
            return result;
        }


        System.out.println("价格校验 Handler 通过...");

        //执行下一个处理器
        return super.next(param);
    }
}
