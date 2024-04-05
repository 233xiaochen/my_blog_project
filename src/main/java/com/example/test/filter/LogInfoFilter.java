package com.example.test.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author： chenlin
 * @create： 2024/3/18 1:04
 */
@Configuration
@Slf4j
public class LogInfoFilter extends OncePerRequestFilter implements Ordered {

    private static final String traceId = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String mdcTraceId = getMdcTraceId();
        MDC.put(traceId,mdcTraceId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = format.format(date);
        log.info("Time          : " +time);
        log.info("URL           : " +request.getRequestURI());
        log.info("HTTP Method   : " +request.getMethod());
        log.info("Class method  : " +request.getServletPath() + "." + request.getMethod());
        log.info("IP            : " +request.getRemoteHost());
        filterChain.doFilter(request, response);
        MDC.clear();
    }

    /**
     * 生产traceId
     * @return traceId
     */
    public String getMdcTraceId() {
        long currentTime = System.nanoTime();
        return String.join("_",traceId,String.valueOf(currentTime));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
