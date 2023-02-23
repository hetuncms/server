package com.hetun.datacenter.websocket.client;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

public interface ReConnectable {
    void reconnect(Session session, CloseReason closeReason);
}
