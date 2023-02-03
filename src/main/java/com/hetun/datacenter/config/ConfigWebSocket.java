//package com.hetun.datacenter.config;
//
//import com.hetun.datacenter.websocket.WebSocketClient;
//import jakarta.websocket.*;
//import org.springframework.stereotype.Component;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLEngine;
//import javax.net.ssl.X509ExtendedTrustManager;
//import java.io.IOException;
//import java.net.Socket;
//import java.net.URI;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static org.apache.tomcat.websocket.Constants.SSL_CONTEXT_PROPERTY;
//
//@Component
//public class ConfigWebSocket {
//    public ConfigWebSocket() {
//
//        X509ExtendedTrustManager x509ExtendedTrustManager = new X509ExtendedTrustManager() {
//            @Override
//            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//            }
//
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return new X509Certificate[0];
//            }
//
//            @Override
//            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//
//            }
//        };
//        SSLContext sslContext = null;
//        try {
//            sslContext = SSLContext.getInstance("SSL");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            sslContext.init(null, new X509ExtendedTrustManager[]{x509ExtendedTrustManager},new SecureRandom());
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
//        }
//
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder
//                .create().configurator(new ClientEndpointConfig.Configurator(){
//                    @Override
//                    public void beforeRequest(Map<String, List<String>> headers) {
//                        super.beforeRequest(headers);
//                        List<String> values = new ArrayList<String>();
//                        values.add("v");
//                        headers.put("k",values);
//                    }
//                }).build();
//        clientEndpointConfig.getUserProperties().put(SSL_CONTEXT_PROPERTY, sslContext);
//        try {
//            Session session = container.connectToServer(new WebSocketClient(),clientEndpointConfig, URI.create("wss://sports.dawnbyte.com/soccer-stream/ws"));
//        } catch (DeploymentException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//}
