package com.hetun.datacenter.websocket.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class PingExecutorService {

    public static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3);

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
