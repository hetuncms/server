package com.hetun.datacenter.websocket.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class AllWebSocketClientBeans {
    private final FootballCallBack footballCallBack;
    private final OuYaRateOddsCallBack ouYaRateOddsCallBack;
    private final BasketballCallBack basketballCallBack;

    @Autowired
    public AllWebSocketClientBeans(OuYaRateOddsCallBack ouYaRateOddsCallBack, BasketballCallBack basketballCallBack, FootballCallBack footballCallBack) {
        this.ouYaRateOddsCallBack = ouYaRateOddsCallBack;
        this.basketballCallBack = basketballCallBack;
        this.footballCallBack = footballCallBack;
    }

    @Bean
    public DataCenterWebSocketClient ouYa() {
        return new DataCenterWebSocketClient(URI.create("wss://sports.dawnbyte.com/rate-stream/ws"), ouYaRateOddsCallBack);
    }

    @Bean
    public DataCenterWebSocketClient basketball() {
        return new DataCenterWebSocketClient(URI.create("wss://sports.dawnbyte.com/basketball-stream/ws"), basketballCallBack);
    }

    @Bean
    public DataCenterWebSocketClient soccer() {
        return new DataCenterWebSocketClient(URI.create("wss://sports.dawnbyte.com/soccer-stream/ws"), footballCallBack);
    }
}
