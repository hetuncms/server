package com.hetun.datacenter.net;

import com.hetun.datacenter.Config;
import com.hetun.datacenter.tripartite.net.ArgsInfoInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Service
public class NetService {

    private final Retrofit retrofit;

    @Autowired
    public NetService(Config config) {
        Retrofit.Builder builder = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(JacksonConverterFactory.create());

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);

        okHttpBuilder.addInterceptor(new ArgsInfoInterceptor());
        if (config.getUseProxy()) {
            java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 10809));
            okHttpBuilder = okHttpBuilder.proxy(proxy);
        }

        OkHttpClient build = okHttpBuilder.build();

        builder.client(build);
        retrofit = builder.baseUrl("https://sports.dawnbyte.com/").build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
