package org.com.itpple.spot.server.global.config;

import org.com.itpple.spot.server.global.log.ContentCacheFilter;
import org.com.itpple.spot.server.global.log.LogFilter;
import org.com.itpple.spot.server.global.log.LogRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public FilterRegistrationBean<LogFilter> LogFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(1);
        registrationBean.setName("LogFilter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ContentCacheFilter> ContentCacheFilter() {
        FilterRegistrationBean<ContentCacheFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ContentCacheFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(1);
        registrationBean.setName("ContentCacheFilter");
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<LogRequestFilter> LogRequestFilter() {
        FilterRegistrationBean<LogRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogRequestFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(3);
        registrationBean.setName("LogRequestFilter");
        return registrationBean;
    }
}
