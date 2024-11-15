package org.ddd.fundamental.workprocess.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workprocess.exception.BadRequestException;
import org.ddd.fundamental.workprocess.exception.ProductNotFoundException;
import org.ddd.fundamental.workprocess.exception.ProductServiceNotAvailableException;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        //这个方法是比如服务是启动的才能打印出信息
        //如果服务挂掉是不能打印出信息的
        log.info("response code is {}",response.status());
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new ProductNotFoundException("Product not found");
            case 503:
                return new ProductServiceNotAvailableException("Product Api is unavailable");
            default:
                return new Exception("Exception while getting product details");
        }
    }
}
