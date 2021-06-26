package com.daib.backend.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        log.info("RequestBody : {} / ResponseBody : {}", objectMapper.readTree(cachingRequest.getContentAsByteArray()), objectMapper.readTree(cachingResponse.getContentAsByteArray()));


    }
}
