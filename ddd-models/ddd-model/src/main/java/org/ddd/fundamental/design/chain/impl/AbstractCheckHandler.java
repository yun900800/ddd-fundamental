package org.ddd.fundamental.design.chain.impl;

import org.ddd.fundamental.design.chain.ProductCheckHandlerConfig;
import org.ddd.fundamental.design.model.ProductVO;
import org.ddd.fundamental.design.model.Result;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public abstract class AbstractCheckHandler {

    /**
     * 子类可以拿到这个对象
     */
    protected ProductCheckHandlerConfig config;

    private AbstractCheckHandler nextHandler;

    public ProductCheckHandlerConfig getConfig() {
        return config;
    }

    public void setConfig(ProductCheckHandlerConfig config) {
        this.config = config;
    }

    public AbstractCheckHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(AbstractCheckHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Result handle(ProductVO productVO);

    protected Result next(ProductVO param) {
        //下一个链路没有处理器了，直接返回
        if (Objects.isNull(nextHandler)) {
            return Result.success();
        }

        //执行下一个处理器
        return nextHandler.handle(param);
    }

}
