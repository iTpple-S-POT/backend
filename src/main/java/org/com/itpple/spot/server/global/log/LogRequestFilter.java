package org.com.itpple.spot.server.global.log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;


@Slf4j
public class LogRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var requestMethod = request.getMethod();
        var requestURN = getRequestURN(request);
        var requestBody = getRequestBody(request);

        log.info(requestMethod + " " + requestURN + ", requestBody: " + requestBody);

        filterChain.doFilter(request, response);
    }

    private String getRequestURN(HttpServletRequest request) {
        return request.getRequestURI() + (request.getQueryString() != null ? "?"
                + request.getQueryString() : "");
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        if(request.getContentType() == null || !request.getContentType().contains("application/json")) {
            return "not json";
        }

        var cachedRequest = (ContentCachingRequestWrapper) request;
        var requestBodyBytes = cachedRequest.getContentAsByteArray();

        return new String(requestBodyBytes, StandardCharsets.UTF_8).replaceAll("\\s+",
                "");
    }
}
