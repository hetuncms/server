package com.hetun.datacenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${local_address}")
    private String localAddress;


    @Value("${spring.web.resources.static-locations}")
    private String staticPath;

    public String getStaticPath() {
        return staticPath;
    }

    public String getLocalAddress() {
        return localAddress;
    }

}
