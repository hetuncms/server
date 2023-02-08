package com.hetun.datacenter.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.BallTeamService;
import com.hetun.datacenter.service.IndexService;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FootballReadTimeHandler extends Endpoint {
    private final IndexService indexService;
    private final BallTeamService ballTeamService;

    @Autowired
    public FootballReadTimeHandler(IndexService indexService, BallTeamService ballTeamService) {
        this.indexService = indexService;
        this.ballTeamService = ballTeamService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ObjectMapper objectMapper = new ObjectMapper();
                WssBean wssBean;
                try {
                    wssBean = objectMapper.readValue(message, WssBean.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                if (wssBean.getType() == 101) {
                    switch (wssBean.getAction_type()) {
                        case 1:ballTeamService.addTeam(wssBean.getPayload());break;
                        case 2:ballTeamService.updateTeam(wssBean.getPayload());break;
                        case 3:ballTeamService.delTeam(wssBean.getPayload());break;
                    }
                }

            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        System.out.println("MyWebSocketClient.onClose" + closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
        System.out.println(session);
    }
}
