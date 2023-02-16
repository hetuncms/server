package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.tripartite.service.BasketballService;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class BasketballStream extends Endpoint {
    BasketballService basketballService;

    public BasketballStream(BasketballService basketballService) {
        this.basketballService = basketballService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
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
//                            rateOddsService.handlerOuYa(wssBean.getPayload());
                        }
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
