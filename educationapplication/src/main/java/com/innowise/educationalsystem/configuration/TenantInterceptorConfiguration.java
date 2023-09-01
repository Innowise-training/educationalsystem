package com.innowise.educationalsystem.configuration;

import com.innowise.educationalsystem.feign.ConnectionDetailsClient;
import com.innowise.educationalsystem.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class TenantInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    ConnectionDetailsClient connectionDetailsClient;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor(connectionDetailsClient)).excludePathPatterns("/api/v1/subscription/**");
    }
}
