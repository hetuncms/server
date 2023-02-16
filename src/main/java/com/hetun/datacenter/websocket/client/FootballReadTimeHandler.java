package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.BallTeamService;
import com.hetun.datacenter.service.IndexService;
import com.hetun.datacenter.tripartite.bean.SoccerInfoRealTime;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class FootballReadTimeHandler extends BaseEndpoint {
    private final IndexService indexService;
    private final BallTeamService ballTeamService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public FootballReadTimeHandler(IndexService indexService, BallTeamService ballTeamService, SimpMessagingTemplate simpMessagingTemplate) {
        this.indexService = indexService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.ballTeamService = ballTeamService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        super.onOpen(session, config);
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                if (message.equals("ping")) {
                    return;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                WssBean wssBean;
                try {
                    wssBean = objectMapper.readValue(message, WssBean.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return;
                }
                String payload = wssBean.getPayload();
                if (wssBean.getType() == 101) {
                    switch (wssBean.getAction_type()) {
                        case 1:ballTeamService.addTeam(payload);break;
                        case 2:ballTeamService.updateTeam(payload);break;
                        case 3:ballTeamService.delTeam(payload);break;
                    }
                }

                try {
                    if (wssBean.getType()==301) {
                        SoccerInfoRealTime soccerInfoRealTime = objectMapper.readValue(wssBean.getPayload(), SoccerInfoRealTime.class);
                        simpMessagingTemplate.convertAndSend("/topic/soccerInfo", soccerInfoRealTime);
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
        System.out.println("FootballReadTimeHandler.onClose" + closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
        System.out.println("FootballReadTimeHandler.onError" + throwable);
    }
}
