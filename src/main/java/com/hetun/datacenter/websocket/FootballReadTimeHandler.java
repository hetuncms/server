package com.hetun.datacenter.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.service.IndexService;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;

@Component
public class FootballReadTimeHandler extends Endpoint  {
    private final IndexService indexService;
    public FootballReadTimeHandler(IndexService indexService) {
        this.indexService = indexService;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ObjectMapper objectMapper = new ObjectMapper();
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
