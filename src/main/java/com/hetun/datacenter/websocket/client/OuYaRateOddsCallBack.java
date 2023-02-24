package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.RateOddsService;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

@Component
public class OuYaRateOddsCallBack implements DataCenterWebSocketClient.CallBack {
    private final RateOddsService rateOddsService;

    public OuYaRateOddsCallBack(RateOddsService rateOddsService) {
        this.rateOddsService = rateOddsService;
    }



    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WssBean wssBean = objectMapper.readValue(message, WssBean.class);
            if (wssBean.getType() == 802) {
                if (wssBean.getAction_type() == 2) {
                    switch (wssBean.getSport_id()) {
                        case 101 -> rateOddsService.handlerOuYaFootball(wssBean.getPayload());
                        case 102 -> rateOddsService.handlerOuYaBasketball(wssBean.getPayload());
                    }
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
