package com.example.filter.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");

        var handlerMethod = (HandlerMethod) handler;

        var methodLevel = handlerMethod.getMethodAnnotation(OpenApi.class);
        if(methodLevel != null){
            log.info("methodLevel : {}", methodLevel);
            return true;
        }

        var classLevel = handlerMethod.getBeanType().getAnnotation(OpenApi.class);
        if(classLevel != null) {
            log.info("classLevel : {}", classLevel);
            return true;
        }

        log.info("openapi 아님 : {}", request.getRequestURI());

        // controller 전달, false면 전달하지 않음
        // return HandlerInterceptor.super.preHandle(request, response, handler);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // controller 이후 동작
        log.info("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 요청 처리와 뷰 렌더링까지 모두 완료된 뒤 마지막에 실행되는 단계 (예외 발생 여부와 관계없이 호출됨)
        log.info("afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
