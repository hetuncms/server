package com.hetun.datacenter.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NetInterface {

    @GET
    Call<ResponseBody> downloadImg(@Url String url);

    @GET
    Call<ResponseBody> verifyVideoUrl(@Url String url);
}
