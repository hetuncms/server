package com.hetun.datacenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    Config config;

    @Autowired
    public WebMvcConfig(Config config) {
        this.config = config;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        if(!config.getStaticPath().startsWith("classpath")){
            registry.addResourceHandler("/**").addResourceLocations("file:"+config.getStaticPath());
        }
    }
}
