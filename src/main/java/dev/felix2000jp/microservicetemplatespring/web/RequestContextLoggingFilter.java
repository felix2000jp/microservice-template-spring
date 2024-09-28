package dev.felix2000jp.microservicetemplatespring.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
@Component
public class RequestContextLoggingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestContextLoggingFilter.class);

    private final static String correlationIdHeaderName = "Correlation-Id";

    private final static String correlationIdLogName = "correlationId";
    private final static String requestPathLogName = "requestPath";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var requestPath = request.getRequestURI();
        var correlationId = request.getHeader(correlationIdHeaderName);

        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        response.setHeader(correlationIdHeaderName, correlationId);

        MDC.put(correlationIdLogName, correlationId);
        MDC.put(requestPathLogName, requestPath);

        logger.info("Starting request to: {}", requestPath);
        filterChain.doFilter(request, response);
        logger.info("Finished request with: {}", response.getStatus());

        MDC.clear();
    }

}
