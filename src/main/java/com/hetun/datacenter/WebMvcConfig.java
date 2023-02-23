package com.hetun.datacenter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final Config config;

    @Autowired
    public WebMvcConfig(Config config) {
        this.config = config;
    }

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        if(!config.getStaticPath().startsWith("classpath")){
            registry.addResourceHandler("/**").addResourceLocations("file:"+config.getStaticPath());
        }
    }
}
