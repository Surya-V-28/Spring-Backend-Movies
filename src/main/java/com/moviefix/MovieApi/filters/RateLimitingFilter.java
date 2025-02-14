package com.moviefix.MovieApi.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 2; // Limit per time window
    private static final long TIME_WINDOW = 1800000; // 180 seconds

    private final Map<String, RequestInfo> ipRequestMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        ipRequestMap.putIfAbsent(clientIp, new RequestInfo(currentTime, new AtomicInteger(0)));
        RequestInfo requestInfo = ipRequestMap.get(clientIp);

        synchronized (requestInfo) {
            if (currentTime - requestInfo.startTime > TIME_WINDOW) {
                requestInfo.startTime = currentTime;
                requestInfo.requestCount.set(0);
            }

            if (requestInfo.requestCount.incrementAndGet() > MAX_REQUESTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Rate limit exceeded. Try again later.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private static class RequestInfo {
        long startTime;
        AtomicInteger requestCount;

        RequestInfo(long startTime, AtomicInteger requestCount) {
            this.startTime = startTime;
            this.requestCount = requestCount;
        }
    }
}
