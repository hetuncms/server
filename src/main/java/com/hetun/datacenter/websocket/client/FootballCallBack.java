package com.hetun.datacenter.websocket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.WssBean;
import com.hetun.datacenter.service.BallTeamService;
import com.hetun.datacenter.tripartite.bean.SoccerInfoRealTime;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class FootballCallBack implements DataCenterWebSocketClient.CallBack {
    private final BallTeamService ballTeamService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public FootballCallBack(BallTeamService ballTeamService, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.ballTeamService = ballTeamService;
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
                case 1 -> ballTeamService.addTeam(payload);
                case 2 -> ballTeamService.updateTeam(payload);
                case 3 -> ballTeamService.delTeam(payload);
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

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
