package com.selab.webexample.config;

import com.selab.webexample.intercepter.ViewControllerResourceTypeInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${network.area:wan}")
    private String resourceType;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ViewControllerResourceTypeInterceptor(resourceType))
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/logout","/loginFail","/accessDenied","/vendor/**","/js/**","/css/**");

    }
}
