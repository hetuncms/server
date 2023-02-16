package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.RateOddsService;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;

@Component
public class OuYaRateOddsWebSocketClient extends BaseEndpoint {
    private final RateOddsService rateOddsService;

    public OuYaRateOddsWebSocketClient(RateOddsService rateOddsService) {
        this.rateOddsService = rateOddsService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        super.onOpen(session, config);
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    WssBean wssBean = objectMapper.readValue(message, WssBean.class);
                    if (wssBean.getType() == 802) {
                        if (wssBean.getAction_type() == 2) {
                            switch (wssBean.getSport_id()) {
                                case 101:
                                    rateOddsService.handlerOuYaFootball(wssBean.getPayload());
                                    break;
                                case 102:
                                    rateOddsService.handlerOuYaBasketball(wssBean.getPayload());
                                    break;
                            }
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

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        System.out.println("OuYaRateOddsWebSocketClient.onClose" + closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
        System.out.println("OuYaRateOddsWebSocketClient" + session+"===");
        throwable.printStackTrace();
    }
}
