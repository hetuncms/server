package com.hetun.datacenter.tripartite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.tripartite.bean.BasketballMatchInfoBean;
import org.springframework.stereotype.Service;

@Service
public class BasketballService {
    public void addMatchInfo(String payload) {
        try {
            BasketballMatchInfoBean basketballMatchInfoBean = new ObjectMapper().readValue(payload, BasketballMatchInfoBean.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
