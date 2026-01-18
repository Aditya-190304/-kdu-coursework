package com.libraryweb.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j // i added this to enable logging
public class CorrelationFilter implements Filter {

    private static final String HEADER_NAME = "X-Correlation-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        String id = req.getHeader(HEADER_NAME);
        // i generate a new uuid if the client didn't provide one
        if (id == null || id.isBlank()) id = UUID.randomUUID().toString();

        MDC.put("correlationId", id); // i put the id in the MDC so it automatically attaches to every log line
        res.addHeader(HEADER_NAME, id); // i send the id back to the client so they can reference it if they need support


        log.info("Request Started: {} {}", req.getMethod(), req.getRequestURI());

        try {
            chain.doFilter(request, response); // i let the request proceed to the controller
        } finally {

            long duration = System.currentTimeMillis() - start;

            // i am logging exactly what was asked in Phase 9
            log.info("Request Ended: method={} path={} status={} latency={}ms",
                    req.getMethod(),
                    req.getRequestURI(),
                    res.getStatus(),
                    duration);

            MDC.remove("correlationId"); // i remove the id from the thread context to avoid memory leaks or pollution
        }
    }
}