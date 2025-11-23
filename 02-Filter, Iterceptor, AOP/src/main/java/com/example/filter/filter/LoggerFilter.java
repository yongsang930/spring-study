package com.example.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
//@Component
public class LoggerFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("진입:::::::::::");

        // 필터가 읽어도 → Spring MVC가 Body를 다시 읽을 수 있도록 스트림을 다시 제공
        var req  = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);

        var res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

       /*
        var req  = new HttpServletRequestWrapper((HttpServletRequest) servletRequest);

        // 응답 body를 읽는 기능이 없다
        var res = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);

        var br = req.getReader();

        var list = br.lines().collect(Collectors.toList());

        list.forEach( it -> {
            log.info("{}",it);
        });
            → 이런 식으로 필터에서 먼저 Stream을 읽었기 때문에 이후 cotroller에서 body의 json을 객체에 넣어주지 못함
            -- HTTP Request Body는 InputStream 형태로 전달되는데 스트림(stream)은 1회성이다
            -- 'getReader() has already been called for this request' 에러 발생
        */

        filterChain.doFilter(req, res);

        var reqJson = new String(req.getContentAsByteArray());
        log.info("reqJson : {}", reqJson);

        /*
            1. controller가 응답을 씀
            2. Wrapper가 그 내용을 가로채서 내부 캐시에 저장, 하지만 실제 사용자에게는 아직 전송되지 않음
            3. 필터에서 log를 찍고 난 뒤 res.copyBodyToResponse()를 호출해야 실제 HTTP Response Body로 복사
        */
        var resJson = new String(res.getContentAsByteArray());
        log.info("resJson : {}", resJson);

        log.info("리턴:::::::::::");

        res.copyBodyToResponse();
    }
}
