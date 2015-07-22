package com.feedlyclone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**/*").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }
}
