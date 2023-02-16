package com.hetun.datacenter.websocket.client;

import jakarta.websocket.MessageHandler;

public abstract class BaseMessageHandler implements MessageHandler.Whole<String> {
    @Override
    public void onMessage(String message) {

    }
}
