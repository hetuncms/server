package com.hetun.datacenter.websocket.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class PingExecutorService {

    public static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3);

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
