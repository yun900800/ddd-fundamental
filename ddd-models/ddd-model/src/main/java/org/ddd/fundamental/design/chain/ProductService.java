package org.ddd.fundamental.design.chain;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.ddd.fundamental.design.chain.impl.AbstractCheckHandler;
import org.ddd.fundamental.design.chain.impl.HandlerExecutor;
import org.ddd.fundamental.design.model.ProductVO;
import org.ddd.fundamental.design.model.Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Component
public class ProductService {

    /**
     * 使用Spring注入:所有继承了AbstractCheckHandler抽象类的Spring Bean都会注入进来。Map的Key对应Bean的name,Value是name对应相应的Bean
     */
    @Resource
    private Map<String, AbstractCheckHandler> handlerMap;

    /**
     * 创建商品
     * @return
     */
    public Result createProduct(ProductVO param) {

        //参数校验，使用责任链模式
        Result paramCheckResult = this.paramCheckByChain(param);
        if (!paramCheckResult.isSuccess()) {
            return paramCheckResult;
        }

        //创建商品
        return this.saveProduct(param);
    }

    /**
     * 参数校验：责任链模式
     * @param param
     * @return
     */
    private Result paramCheckByChain(ProductVO param) {

        //构建处理器配置：通常配置使用统一配置中心存储，支持动态变更
        ProductCheckHandlerConfig handlerConfig = buildHandlerConfigFile();

        //构建处理器
        AbstractCheckHandler handler = buildHandlers(handlerConfig,handlerMap);

        //责任链：执行处理器链路
        Result executeChainResult = HandlerExecutor.executeChain(handler, param);
        if (!executeChainResult.isSuccess()) {
            System.out.println("创建商品 失败...");
            return executeChainResult;
        }

        //处理器链路全部成功
        return Result.success();
    }

    /**
     * 获取处理器配置：通常配置使用统一配置中心存储，支持动态变更
     * @return
     */
    private static ProductCheckHandlerConfig buildHandlerConfigFile() {
        //配置中心存储的配置
        String configJson = "{\"handler\":\"nullValueCheckHandler\",\"down\":false,\"next\":{\"handler\":\"priceCheckHandler\",\"next\":{\"handler\":\"stockCheckHandler\",\"next\":null}}}";
        //转成Config对象
        ProductCheckHandlerConfig handlerConfig = JSON.parseObject(configJson, ProductCheckHandlerConfig.class);
        return handlerConfig;
    }

    private static boolean isBuildHandlersFinished(ProductCheckHandlerConfig config, Map<String, AbstractCheckHandler> handlerMap) {
        if (Objects.isNull(config) || StringUtils.isBlank(config.getHandler()) ||
                Objects.isNull(handlerMap.get(config.getHandler()))) {
            return true;
        }
        return false;
    }

    /**
     * 获取处理器
     * @param config
     * @return
     */
    private static AbstractCheckHandler buildHandlers(ProductCheckHandlerConfig config, Map<String, AbstractCheckHandler> handlerMap) {
        if (isBuildHandlersFinished(config,handlerMap)) {
            return null;
        }
        AbstractCheckHandler abstractCheckHandler = handlerMap.get(config.getHandler());
        //处理器设置配置Config
        abstractCheckHandler.setConfig(config);

        //递归设置链路处理器
        abstractCheckHandler.setNextHandler(buildHandlers(config.getNext(),handlerMap));

        return abstractCheckHandler;
    }


    private Result saveProduct(ProductVO param) {
        System.out.println("保存商品 成功...");
        return Result.success();
    }
}
