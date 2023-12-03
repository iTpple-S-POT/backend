package org.com.itpple.spot.server.config;

import org.com.itpple.spot.server.common.log.LogFilter;
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

}
