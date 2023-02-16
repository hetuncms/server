package com.hetun.datacenter.websocket.client;

import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public abstract class BaseEndpoint extends Endpoint {
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        Runnable r = () -> {
            String data = "Ping";
            ByteBuffer payload = ByteBuffer.wrap(data.getBytes());
            if (session != null) {
                try {
                    session.getBasicRemote().sendPing(payload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        PingExecutorService.getScheduledExecutorService().scheduleAtFixedRate(r, 5, 120, TimeUnit.SECONDS);
    }
}
