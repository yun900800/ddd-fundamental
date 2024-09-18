package org.ddd.fundamental.design.chain.impl;

import org.ddd.fundamental.constants.ErrorCode;
import org.ddd.fundamental.design.model.ProductVO;
import org.ddd.fundamental.design.model.Result;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NullValueCheckHandler extends AbstractCheckHandler {

    @Override
    public Result handle(ProductVO param) {
        System.out.println("空值校验 Handler 开始...");

        //降级：如果配置了降级，则跳过此处理器，执行下一个处理器
        if (super.getConfig().getDown()) {
            System.out.println("空值校验 Handler 已降级，跳过空值校验 Handler...");
            return super.next(param);
        }
        Result result = param.nullValueCheck();
        if (!result.isSuccess()) {
            return result;
        }
        System.out.println("空值校验 Handler 通过...");

        //执行下一个处理器
        return super.next(param);
    }
}
