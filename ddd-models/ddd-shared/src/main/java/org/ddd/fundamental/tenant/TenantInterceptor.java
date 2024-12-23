package org.ddd.fundamental.tenant;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.thread.RequestUserContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TenantInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        RequestUserContext.setUser(new RequestUserContext.User("bf0e30d6-1fc6-4874-91b3-af352404e50a"));
        log.info("start set TenantInfo finished");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
