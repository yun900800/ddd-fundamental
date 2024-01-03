package org.ddd.fundamental.app.exception;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.BindException;

@Component
@ControllerAdvice
public class CustomExceptionHandlerResolver extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(value= BindException.class)
    protected ModelAndView handleBindException(
            BindException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
            throws IOException {
        System.out.println("In CustomExceptionHandlerResolver");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView();
        //参考错误处理https://www.baeldung.com/exception-handling-for-rest-with-spring
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
