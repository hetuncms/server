package com.hetun.datacenter.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.RateOddsService;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;

@Component
public class MyWebSocketClient extends Endpoint  {
    private final RateOddsService rateOddsService;
    public MyWebSocketClient(RateOddsService rateOddsService) {
        this.rateOddsService = rateOddsService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(new MessageHandler.Whole<String >() {
            @Override
            public void onMessage(String message) {
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    WssBean wssBean = objectMapper.readValue(message, WssBean.class);
                    if (wssBean.getType()==802) {
                        if (wssBean.getAction_type()==2) {
                            rateOddsService.handlerOuYa(wssBean.getPayload());
                        }
                    }else if(wssBean.getType() ==801){
                        if (wssBean.getAction_type()==1) {
//                            rateOddsService.handlerOuYa(wssBean.getPayload());
                        }
                    }
                } catch (JsonProcessingException e) {
                  e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        System.out.println("MyWebSocketClient.onClose"+closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
        System.out.println(session);
    }
}
