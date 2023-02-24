package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.tripartite.service.BasketballService;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketballCallBack implements DataCenterWebSocketClient.CallBack{
    final BasketballService basketballService;


    @Autowired
    public BasketballCallBack(BasketballService basketballService) {
        this.basketballService = basketballService;
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        if (message.equals("ping")) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WssBean wssBean = objectMapper.readValue(message, WssBean.class);

            if (wssBean.getType() == 301) {
                if (wssBean.getAction_type() == 1) {
                    basketballService.addMatchInfo(wssBean.getPayload());
                }
            } else if (wssBean.getType() == 801) {
                if (wssBean.getAction_type() == 1) {
                    // todo
//                            rateOddsService.handlerOuYa(wssBean.getPayload());
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
