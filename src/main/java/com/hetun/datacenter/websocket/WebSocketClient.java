package com.hetun.datacenter.websocket;

import jakarta.websocket.*;

import java.io.IOException;

public class WebSocketClient extends Endpoint {
    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        System.out.println(session);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);

    }

    @Override
    public void onOpen(final Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String s) {
                try {
                    onHandleMessage(session, s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            session.getBasicRemote().sendText("ABC");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onHandleMessage(Session session, String message) throws IOException {
        if ("DEF".equals(message)){
            session.close();
        }
        System.out.println(message);
    }
}
