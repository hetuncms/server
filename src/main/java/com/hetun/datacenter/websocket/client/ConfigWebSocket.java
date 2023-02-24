package com.hetun.datacenter.websocket.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigWebSocket {


    private final DataCenterWebSocketClient ouYa;
    private final DataCenterWebSocketClient basketball;
    private final DataCenterWebSocketClient soccer;

    @Autowired
    public ConfigWebSocket(DataCenterWebSocketClient ouYa,DataCenterWebSocketClient basketball,DataCenterWebSocketClient soccer) {
        this.ouYa = ouYa;
        this.basketball = basketball;
        this.soccer = soccer;
        init();
    }

    public void init() {
        connect();
    }

    public void connect() {
        ouYa.connect();
        basketball.connect();
        soccer.connect();
    }
}
