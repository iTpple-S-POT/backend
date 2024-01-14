package org.com.itpple.spot.server.global.log;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class ContentCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        var cachedBodyHttpServletRequest =
                new ContentCachingRequestWrapper((HttpServletRequest) request);

        chain.doFilter(cachedBodyHttpServletRequest, response);
    }
}
