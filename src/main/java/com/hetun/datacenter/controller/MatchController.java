package com.hetun.datacenter.controller;

import com.hetun.datacenter.net.MatchInterface;
import com.hetun.datacenter.net.NetService;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class MatchController {

    MatchInterface matchInterface;

    @Autowired
    public MatchController(NetService netService) {
        matchInterface = netService.getRetrofit().create(MatchInterface.class);
    }

    @PostMapping("match")
    public String match(){
        try {
            ResponseBody body = matchInterface.match(URLEncoder.encode("act=1&sign=cl2iNsvtc2m0U2O0O0O2")).execute().body();
            System.out.println(body.string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
