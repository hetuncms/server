package com.hetun.datacenter.websocket.client;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

public interface ReConnectable {
    public void reconnect(Session session, CloseReason closeReason);
}
