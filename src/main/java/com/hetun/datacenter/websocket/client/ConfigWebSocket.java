package com.hetun.datacenter.websocket.client;

import com.hetun.datacenter.service.BallTeamService;
import com.hetun.datacenter.service.IndexService;
import com.hetun.datacenter.service.RateOddsService;
import com.hetun.datacenter.tripartite.service.BasketballService;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ConfigWebSocket implements ReConnectable {


    private final RateOddsService rateOddsService;
    private final IndexService indexService;
    private final BallTeamService ballTeamService;
    private final FootballReadTimeHandler footballReadTimeHandler;
    BasketballService basketballService;
    WebSocketContainer container;
    ClientEndpointConfig clientEndpointConfig;
    @Autowired
    public ConfigWebSocket(RateOddsService rateOddsService, FootballReadTimeHandler footballReadTimeHandler, IndexService indexService, BallTeamService ballTeamService, BasketballService basketballService) {
        this.rateOddsService = rateOddsService;
        this.indexService = indexService;
        this.footballReadTimeHandler = footballReadTimeHandler;
        this.ballTeamService = ballTeamService;
        this.basketballService = basketballService;

        init();
    }

    public void init() {
        container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(128 * 1024 * 1024);

        int maxsize = 200 * 1024;
        container.setDefaultMaxBinaryMessageBufferSize(maxsize);
        container.setDefaultMaxTextMessageBufferSize(maxsize);
        clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        connect();
    }

    public void connect() {
        OuYaRateOddsWebSocketClient ouYaRateOddsWebSocketClient = new OuYaRateOddsWebSocketClient(rateOddsService);
        try {
            container.connectToServer(ouYaRateOddsWebSocketClient, clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/rate-stream/ws"));
            container.connectToServer(new BasketballStream(basketballService), clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/basketball-stream/ws"));
            container.connectToServer(footballReadTimeHandler, clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/soccer-stream/ws"));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnect(Session session, CloseReason closeReason) {
        System.out.println("reconnecting:"+(closeReason!=null?closeReason:""));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        connect();
    }
}
