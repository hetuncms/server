package com.hetun.datacenter.websocket.client;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Slf4j
public class DataCenterWebSocketClient extends WebSocketClient {

    private final CallBack callBack;
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();


    public DataCenterWebSocketClient(URI serverUri, @NotNull CallBack callBack) {
        super(serverUri);
        this.callBack = callBack;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("onOpen() called with: handshakedata = [" + handshakedata + "]");
        callBack.onOpen(handshakedata);
    }

    @Override
    public void onMessage(String message) {
        log.info("onMessage() called with: message = [" + message + "]");
        callBack.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        callBack.onClose(code, reason, remote);
        log.info("onClose() called with: code = [" + code + "], reason = [" + reason + "], remote = [" + remote + "]");
        schedule.schedule(new Runnable() {
            @Override
            public void run() {
                reconnect();
            }
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onError(Exception ex) {
        log.info("onError() called with: ex = [" + ex + "]");
        callBack.onError(ex);
        ex.printStackTrace();
    }

    public interface CallBack {
        void onOpen(ServerHandshake handshakedata);

        void onMessage(String message);

        void onClose(int code, String reason, boolean remote);

        void onError(Exception ex);
    }
}
