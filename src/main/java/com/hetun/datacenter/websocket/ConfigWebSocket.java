package com.hetun.datacenter.websocket;

import com.hetun.datacenter.service.IndexService;
import com.hetun.datacenter.service.RateOddsService;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ConfigWebSocket {


    private final RateOddsService rateOddsService;
    private final IndexService indexService;

    @Autowired
    public ConfigWebSocket(RateOddsService rateOddsService, IndexService indexService) {
        this.rateOddsService = rateOddsService;
        this.indexService = indexService;
        init();
    }

    public void init() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        container.setDefaultMaxTextMessageBufferSize(54 * 1024 * 1024);
//        container.setDefaultMaxBinaryMessageBufferSize(Integer.MAX_VALUE);
//        container.setDefaultMaxTextMessageBufferSize(Integer.MAX_VALUE);
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        MyWebSocketClient client = new MyWebSocketClient(rateOddsService);
        try {
            Session session = container.connectToServer(client, clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/rate-stream/ws"));
            container.connectToServer(new FootballReadTimeHandler(indexService), clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/soccer-stream/ws"));
            System.out.println(session.getOpenSessions());
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
