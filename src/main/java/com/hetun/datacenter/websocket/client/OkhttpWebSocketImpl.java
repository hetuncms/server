package com.hetun.datacenter.websocket.client;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OkhttpWebSocketImpl {

    WebSocket webSocket;
    public OkhttpWebSocketImpl() {
        connect();
    }

    public void reconnect(){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connect();
        }).start();
    }

    private void connect() {
        if (webSocket!=null) {
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url("wss://sports.dawnbyte.com/rate-stream/ws").build();
        webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("ConfigWebSocket.onClosed");
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("ConfigWebSocket.onClosing");
                OkhttpWebSocketImpl.this.webSocket = null;
                reconnect();
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("ConfigWebSocket.onFailure");
                OkhttpWebSocketImpl.this.webSocket = null;
                reconnect();
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                System.out.println(text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("ConfigWebSocket.onMessage");
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                System.out.println("ConfigWebSocket.onOpen");
            }
        });
    }

}
